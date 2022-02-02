package ru.job4j.threads.cache;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.cache.Base;
import ru.job4j.cache.Cache;
import ru.job4j.cache.OptimisticException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * CacheTest.
 *
 * @author fourbarman (maks.java@yandex.ru).
 * @version %I%, %G%.
 * @since 02.02.2022.
 */
public class CacheTest {
    Cache cache;
    Base model1;
    Base newModel1;
    Base model2;
    Base model3;
    Base model4;

    /**
     * Set variables.
     */
    @Before
    public void setVars() {
        cache = new Cache();
        model1 = new Base(1, 0);
        model1.setName("first ver");
        newModel1 = new Base(1, 0);
        newModel1.setName("NEW first ver");
        model2 = new Base(1, 1);
        model2.setName("second ver");
        model3 = new Base(1, 2);
        model3.setName("third ver");
        model4 = new Base(1, 3);
        model4.setName("fourth ver");
    }

    /**
     * When add object to cache than success and cache has that object.
     */
    @Test
    public void test1() {
        assertThat(cache.add(model1), is(true));
        assertThat(cache.getBase(model1.getId()), is(model1));
    }

    /**
     * When delete object from cache than success.
     */
    @Test
    public void whenDeleteBaseObjectThanSuccess() {
        cache.add(model1);
        cache.delete(model1);
        assertNull(cache.getBase(model1.getId()));
    }

    /**
     * When update and stored Base version is not equal new Base than throw OptimisticException.
     */
    @Test(expected = OptimisticException.class)
    public void whenUpdateAndStoredVersionIsNotEqualToNewModelThanThrowOptimisticException() {
        cache.add(model1);
        cache.update(model3);
    }

    /**
     * When update to new version than success.
     */
    @Test
    public void whenUpdateToNewVersionThanSuccess() {
        cache.add(model1);
        assertThat(cache.update(newModel1).getName(), is(newModel1.getName()));
    }

    /**
     * When update several times version than success.
     */
    @Test
    public void whenUpdateSeveralTimesToNewVersionThanSuccess() {
        cache.add(model1);
        cache.update(newModel1);
        cache.update(model2);
        cache.update(model3);
        cache.update(model4);
        assertThat(cache.getBase(model4.getId()).getVersion(), is(4));
        assertThat(cache.getBase(model4.getId()).getName(), is(model4.getName()));
    }

}
