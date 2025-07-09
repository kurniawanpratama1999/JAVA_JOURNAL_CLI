import models.Transaction;
import services.Services;
import utils.Colour;

import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        /* FINISH AT 7/9/2025 */

        final String typeAccount = "~account";
        final String typeTransaction = "~transaction";
        final String typeExit = "~exit";
        final String typeHelp = "~help";
        final String typeRestart = "~restart";

        final String helpIntro = """
                Type :
                ~account -> for entry the account control
                ~transaction -> for entry the transaction control
                ~restart -> for restart the program
                ~back -> for back to the previous command in control mode
                ~exit -> for exit the program
                NOTE: Create account first and the you can add a transaction""";

        Scanner in = new Scanner(System.in);
        Services services = new Services();

        try {
            while (true) {
                Colour.println("YELLOW", "Type ~help for information ~");
                Colour.print("BLUE", "ENTRY COMMAND: ");
                String input = in.nextLine();

                if (input.equalsIgnoreCase(typeExit)) break;
                else if (input.equalsIgnoreCase(typeRestart)) Colour.println("YELLOW", "~ Start Again ~");
                else if (input.equalsIgnoreCase(typeAccount)) account(in, services);
                else if (input.equalsIgnoreCase(typeTransaction)) transaction(in, services);
                else if (input.equalsIgnoreCase(typeHelp)) {
                    Colour.println("YELLOW", helpIntro);
                    Colour.print("GREEN", "Type any: ");
                    in.nextLine();
                } else Colour.println("RED", "~ Sorry wrong type ~");

                Colour.println("YELLOW", "~ Start Again ~");
            }

            in.close();
        } catch (Exception e) {
            Colour.println("RED", "Error: Something went wrong -> " + e.getMessage());
        } finally {
            Colour.println("GREEN", "~ Program is SAVE ~");
            services.save();
            Colour.println("RED", "~ Program is Stopped ~");
        }
    }

    private static void account(Scanner in, Services service) {
        Colour.println("YELLOW", """
                You're entry the ACCOUNT control.
                Please type the command based on text below
                """);

        while (true) {

            String commands = "| ~ADD | ~UPDATE | ~LIST | ~DELETE | ~SAVE | ~EXIT | ";
            Colour.println("WHITE", "-".repeat(commands.length()));
            Colour.println("PURPLE", commands);
            Colour.println("WHITE", "-".repeat(commands.length()));

            Colour.print("BLUE", "ENTRY COMMAND: ");
            String command = in.nextLine();

            if (command.equalsIgnoreCase("~ADD")) {
                Colour.println("YELLOW", """
                        You will add a new account,
                        so you can use it at transaction control.
                        """);

                Stack<String> inputs = looping(in, "TYPE ACCOUNT NAME");

                if (!inputs.isEmpty()) {
                    String result = service.addNewAccount(inputs.getFirst());
                    System.out.println(result);
                }

            } else if (command.equalsIgnoreCase("~UPDATE")) {
                Colour.println("YELLOW", "You will update an existing account");

                Stack<String> inputs = looping(in, "TYPE EXISTING ACCOUNT", "TYPE NEW ACCOUNT");

                if (!inputs.isEmpty()) {
                    String result = service.updateExistingAccountName(inputs.get(0), inputs.get(1));
                    System.out.println(result);
                }

            } else if (command.equalsIgnoreCase("~LIST")) {
                Colour.println("YELLOW", "You will get the list of account name");

                String result = service.listAccount();
                System.out.println(result);

            } else if (command.equalsIgnoreCase("~DELETE")) {
                Colour.println("YELLOW", "You will delete an existing account");

                Stack<String> inputs = looping(in, "TYPE EXISTING ACCOUNT");

                if (!inputs.isEmpty()) {
                    String result = service.deleteExistingAccountName(inputs.getFirst());
                    System.out.println(result);
                }

            } else if (command.equalsIgnoreCase("~EXIT")) {
                Colour.println("RED", "Exit from account control");
                break;
            } else if (command.equalsIgnoreCase("SAVE")) {
                service.save();
            } else {
                Colour.println("RED", "Wrong type");
            }
        }
    }

    private static void transaction(Scanner in, Services service) {
        Colour.println("YELLOW", """
                You're entry the TRANSACTION control.
                Please type the command based on text below
                """);
        while (true) {
            String commands = "| ~ADD | ~UPDATE | ~LIST | ~DELETE | ~SAVE | ~EXIT | ";
            Colour.println("WHITE", "-".repeat(commands.length()));
            Colour.println("PURPLE", commands);
            Colour.println("WHITE", "-".repeat(commands.length()));

            Colour.print("BLUE", "ENTRY COMMAND: ");
            String command = in.nextLine();

            if (command.equalsIgnoreCase("~ADD")) {
                Colour.println("YELLOW", "You will add a new transactions.\n please insert min. 2 transaction");

                Colour.println("CYAN", "Type: ~DONE for finish the transaction");

                Colour.println("BLUE", "FORMAT  : dd/mm/yyyy | account | debit | credit | detail");
                String example1 = "EXAMPLE1.1: 7/9/2025 | Buy Something | 10000 | 0 | at minimarket";
                Colour.println("WHITE", "-".repeat(example1.length()));
                Colour.println("PURPLE", example1);
                Colour.println("PURPLE", "EXAMPLE1.2: 7/9/2025 | Cash | 0 | 10000 | buy something");
                Colour.println("WHITE", "-".repeat(example1.length()));
                String example2 = "EXAMPLE2.1: 8/9/2025 | Buy Coffee | 25000 | 0 | at minimarket";
                Colour.println("PURPLE", example2);
                Colour.println("PURPLE", "EXAMPLE2.2: 8/9/2025 | Buy Snack | 0 | 15000 | at friend");
                Colour.println("PURPLE", "EXAMPLE2.3: 8/9/2025 | Cash | 0 | 40000 | -");
                Colour.println("WHITE", "-".repeat(example1.length()));


                Stack<Transaction> transactions = looping2(in);

                if (!transactions.isEmpty()) {
                    String result = service.addNewTransaction(transactions);
                    System.out.println(result);
                }
            } else if (command.equalsIgnoreCase("~UPDATE")) {
                while(true) {
                    Colour.println("GREEN", "| ~date | ~account | ~debit | ~credit | ~detail | ~exit |");
                    Colour.print("BLUE", "ENTRY CHOOSE: ");
                    String choose = in.nextLine();
                    if (choose.equalsIgnoreCase("~exit")) {
                        break;
                    } else if (choose.equalsIgnoreCase("~date")) {
                        Colour.println("YELLOW", "You will update date at existing transaction");

                        Stack<String> inputs = looping(in, "REF NUMBER", "ID NUMBER", "NEW DATE (dd/mm/yyyy)");

                        if (!inputs.isEmpty()) {
                            try {
                                Long ref = Long.parseLong(inputs.get(0));
                                Long id = Long.parseLong(inputs.get(1));

                                List<Integer> partDate = Arrays.stream(inputs.get(2).split("/")).map(Integer::parseInt).toList();
                                LocalDate date = LocalDate.of(partDate.get(2), partDate.get(1), partDate.get(0));

                                String result = service.updateExistingTransactionDate(ref, id, date);
                                System.out.println(result);
                            } catch (Exception e) {
                                System.out.println("Format in incorrect");
                            }
                        }
                        break;
                    } else if (choose.equalsIgnoreCase("~account")) {
                        Colour.println("YELLOW", "You will update account at existing transaction");

                        Stack<String> inputs = looping(in, "REF NUMBER", "ID NUMBER", "NEW ACCOUNT");

                        if (!inputs.isEmpty()) {
                            try {
                                Long ref = Long.parseLong(inputs.get(0));
                                Long id = Long.parseLong(inputs.get(1));
                                String account = inputs.get(2);

                                String result = service.updateExistingTransactionAccount(ref, id, account);
                                System.out.println(result);
                            } catch (Exception e) {
                                System.out.println("Format in incorrect");
                            }
                        }

                        break;
                    } else if (choose.equalsIgnoreCase("~debit")) {
                        Colour.println("YELLOW", "You will update debit at existing transaction");

                        Stack<String> inputs = looping(in, "REF NUMBER", "ID NUMBER", "NEW DEBIT");

                        if (!inputs.isEmpty()) {
                            try {
                                Long ref = Long.parseLong(inputs.get(0));
                                Long id = Long.parseLong(inputs.get(1));
                                Long debit = Long.parseLong(inputs.get(2));

                                String result = service.updateExistingTransactionDebit(ref, id, debit);
                                System.out.println(result);
                            } catch (Exception e) {
                                System.out.println("Format in incorrect");
                            }
                        }

                        break;
                    } else if (choose.equalsIgnoreCase("~credit")) {
                        Colour.println("YELLOW", "You will update credit at existing transaction");

                        Stack<String> inputs = looping(in, "REF NUMBER", "ID NUMBER", "NEW CREDIT");

                        if (!inputs.isEmpty()) {
                            try {
                                Long ref = Long.parseLong(inputs.get(0));
                                Long id = Long.parseLong(inputs.get(1));
                                Long credit = Long.parseLong(inputs.get(2));

                                String result = service.updateExistingTransactionCredit(ref, id, credit);
                                System.out.println(result);
                            } catch (Exception e) {
                                System.out.println("Format in incorrect");
                            }
                        }

                        break;
                    } else if (choose.equalsIgnoreCase("~detail")) {
                        Colour.println("YELLOW", "You will update detail at existing transaction");

                        Stack<String> inputs = looping(in, "REF NUMBER", "ID NUMBER", "NEW DETAIL");

                        if (!inputs.isEmpty()) {
                            try {
                                Long ref = Long.parseLong(inputs.get(0));
                                Long id = Long.parseLong(inputs.get(1));
                                String detail = inputs.get(2);

                                String result = service.updateExistingTransactionDetail(ref, id, detail);
                                System.out.println(result);
                            } catch (Exception e) {
                                System.out.println("Format in incorrect");
                            }
                        }

                        break;
                    } else {
                        Colour.println("RED", "Wrong Type");
                    }

                }
            } else if (command.equalsIgnoreCase("~LIST")) {
                String result = service.listTransaction();
                System.out.println(result);
            } else if (command.equalsIgnoreCase("~DELETE")) {
                Colour.println("YELLOW", "You will delete an existing transaction by Ref");

                String longRef = in.nextLine();
                String result = service.deleteExistingTransaction(Long.parseLong(longRef));
                System.out.println(result);

            } else if (command.equalsIgnoreCase("~EXIT")) {
                Colour.println("RED", "Exit from transaction control");
                break;
            } else if (command.equalsIgnoreCase("~SAVE")) {
                service.save();
            } else {
                Colour.println("RED", "Wrong type");
            }

        }
    }

    private static Stack<String> looping(Scanner in, String... str) {
        Stack<String> inputs = new Stack<>();
        for (int i = 0; i < str.length; i++) {
            String command = str[i].toUpperCase() + ": ";
            Colour.print("GREEN", command);
            String input = in.nextLine();

            if (input.equalsIgnoreCase("~back")) {
                i -= 2;
                if (i != 0) inputs.pop();
            } else if (input.equalsIgnoreCase("~exit")) {
                inputs.clear();
                break;
            } else if (input.isEmpty()) i -= 1;
            else if (input.equalsIgnoreCase("null") || input.equalsIgnoreCase("false") || ".,<>+-/\\`!@#$%^&*()".contains(input)) i -= 1;
            else inputs.push(input);
        }

        return inputs;
    }
    private static Stack<Transaction> looping2 (Scanner in) {
        int i = 0;
        Stack<Transaction> transactions = new Stack<>();
        while(true) {
            i += 1;

            Colour.print("BLUE", "TYPE TRANSACTION" + i + ": ");
            String input = in.nextLine();

            List<String> inputs = Arrays.stream(input.split("\\|")).map(String::trim).toList();

            if (input.equalsIgnoreCase("~done")) {
                if (transactions.isEmpty() || transactions.size() < 2) {
                    transactions.clear();
                }
                break;
            }

            if (inputs.size() < 5) {
                Colour.println("YELLOW", "your field is less then 5");
                i -= 1;
            } else if (input.equalsIgnoreCase("~back")) {
                if (i > 1) transactions.pop();
                i -= 1;
            } else if (input.equalsIgnoreCase("~exit")) {
                transactions.clear();
                break;
            } else {
                List<Integer> partDate = Arrays.stream(inputs.get(0).split("[/-]")).map(Integer::parseInt).toList();

                LocalDate date = LocalDate.of(partDate.get(2), partDate.get(1), partDate.get(0));
                String account = inputs.get(1);
                String detail = inputs.get(4);
                long debit = 0;
                long credit = 0;
                try {
                    debit = Long.parseLong(inputs.get(2));
                    credit = Long.parseLong(inputs.get(3));
                } catch (NumberFormatException e) {
                    if (i > 1) transactions.pop();
                    i -= 1;

                    Colour.println("RED", "Debit or Credit are not a number ");
                }

                transactions.push(new Transaction(
                        date,
                        account,
                        debit,
                        credit,
                        detail));
            }
        }

        return transactions;
    }
}