package com.example;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by libin on 3/27/16.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        Java8FeaturesTests.class,
        SynchronizationNLockTests.class,
        AtomicVariableNConcurrentMapTests.class,
        ThreadNExecutorTests.class
})
public class SuiteTest {
}
