package utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import models.*;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Memories {
    private final String root = new File("").getAbsolutePath();
    private final File folder = new File(root, "save");
    private final File file = new File(folder, "expense_tracker.json");
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();

    public Memories() {
        if (!folder.exists()) {
            boolean success = folder.mkdirs();
            if (!success) throw new RuntimeException("Failed to create folder");
        }

        if (!file.exists()) {
            try {
                if (!file.createNewFile()) throw new RuntimeException("Failed to create file save");

                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write("{}");
            } catch (IOException e) {
                throw new RuntimeException("Error to create file save: " + e.getMessage());
            }
        }
    }

    private JsonObject extractFile () {
        try (JsonReader reader = new JsonReader(new FileReader(file))) {return gson.fromJson(reader, JsonObject.class);}
        catch (IOException e) {throw new RuntimeException(e);}
    }

    public Long getID () {
        JsonObject json = extractFile();
        if (json == null) return 0L;
        if (json.isJsonNull() || !json.has("id")) return 0L;

        return json.get("id").getAsLong();
    }

    public Long getRef () {
        JsonObject json = extractFile();
        if (json == null) return 0L;
        if (json.isJsonNull() || !json.has("ref")) return 0L;

        return json.get("ref").getAsLong();
    }

    public Set<Account> getAccounts () {
        JsonObject json = extractFile();
        if (json == null) return new LinkedHashSet<>();
        ;
        if (json.isJsonNull() || !json.has("accounts")) return new LinkedHashSet<>();

        Type accountType = new TypeToken<Set<String>> () {}.getType();
        Set<String> accNames = gson
                .fromJson(json.get("accounts").getAsJsonArray(), accountType);

        return accNames
                .stream().map(Account::new)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Map<Long, LinkedHashSet<Transaction>> getTransactions () {
        JsonObject json = extractFile();
        if (json == null) {
            return new LinkedHashMap<>();
        }

        if (json.isJsonNull() || !json.has("transactions")) {
            return new LinkedHashMap<>();
        }

        Type transactionType = new TypeToken<Map<Long, Set<Transaction>>> () {}.getType();
        return gson.fromJson(json.get("transactions").getAsJsonObject(), transactionType);
    }

    public void save (Long id, Long ref, Map<Long, LinkedHashSet<Transaction>> transactions, Set<Account> accounts) {
        JsonObject collection = new JsonObject();
        collection.addProperty("id", id);

        collection.addProperty("ref", ref);

        collection.add("transactions", gson.toJsonTree(transactions));

        Set<String> accNames = accounts
                .stream().map(Account::getName)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        collection.add("accounts", gson.toJsonTree(accNames));

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {gson.toJson(collection, writer);}
        catch (IOException e) {throw new RuntimeException(e);}
    }
}