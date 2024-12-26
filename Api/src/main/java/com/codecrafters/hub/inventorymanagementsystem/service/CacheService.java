package com.codecrafters.hub.inventorymanagementsystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface CacheService {
    /**
     * Retrieves a value from the cache associated with the specified key and
     * deserializes it into an instance of the specified type.
     *
     * <p>Example usage:</p>
     * <pre>
     *     String key = "myKey";
     *     MyClass value = cacheService.get(key, MyClass.class);
     * </pre>
     *
     * @param key       the key associated with the cached value
     * @param valueType the class type to which the cached value should be deserialized
     * @param <T>      the type of the value to be retrieved
     * @return an {@link Optional} containing the deserialized value if it exists;
     *         otherwise, an empty {@link Optional}
     * @throws JsonProcessingException if there is an error during JSON deserialization
     */
    <T> Optional<T> get(String key, Class<T> valueType) throws JsonProcessingException;

    /**
     * Retrieves a list of values from the cache associated with the specified key
     * and deserializes them into instances of the specified type.
     *
     * <p>Example usage:</p>
     * <pre>
     *     String key = "myListKey";
     *     List&lt;MyClass&gt; myList = cacheService.getMany(key, MyClass.class);
     * </pre>
     *
     * @param key          the key associated with the cached values
     * @param itemValueType the class type to which the cached items should be deserialized
     * @param <T>         the type of the items to be retrieved
     * @return a {@link List} containing the deserialized values if they exist;
     *         otherwise, an empty list
     * @throws JsonProcessingException if there is an error during JSON deserialization
     */
    <T> List<T> getMany(String key, Class<T> itemValueType) throws JsonProcessingException;

    /**
     * Stores a value in the cache as a string associated with the specified key.
     *
     * <p>This method does not set an expiry time for the cached value.</p>
     *
     * <p>Example usage:</p>
     * <pre>
     *     cacheService.putAsString("myKey", myValue);
     * </pre>
     *
     * @param key   the key under which the value will be cached
     * @param value the value to be cached; it will be serialized as a JSON string
     */
    void putAsString(String key, Object value);

    /**
     * Stores a value in the cache as a string associated with the specified key
     * and sets an expiry time for the cached value.
     *
     * <p>Example usage:</p>
     * <pre>
     *     cacheService.putAsStringWithExpiry("myKey", myValue, 10, TimeUnit.MINUTES);
     * </pre>
     *
     * @param key    the key under which the value will be cached
     * @param value  the value to be cached; it will be serialized as a JSON string
     * @param timeout the duration of time before the cached value expires
     * @param unit   the time unit of the timeout parameter
     */
    void putAsStringWithExpiry(String key, Object value, long timeout, TimeUnit unit);

    /**
     * Removes the cached value associated with the specified key from the cache.
     *
     * <p>This method permanently deletes the entry in the cache. If the key does not exist,
     * no action is taken.</p>
     *
     * <p>Example usage:</p>
     * <pre>
     *     cacheService.remove("myKey");
     * </pre>
     *
     * @param key the key of the cached value to be removed
     */
    void remove(String key);
}