package ru.aasmc.nowinandroid.baselineprofile

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import org.junit.Rule
import org.junit.Test

/**
 * Generates a baseline profile which can be copied to `app/src/main/baseline-prof.txt`.
 */
@ExperimentalBaselineProfilesApi
class BaselineProfileGenerator {
    @get:Rule val baselineProfileRule = BaselineProfileRule()

    @Test
    fun startup() =
        baselineProfileRule.collectBaselineProfile(
            packageName = "com.google.samples.apps.nowinandroid.demo.benchmark"
        ) {
            pressHome()
            // This block defines the app's critical user journey. Here we are interested in
            // optimizing for app startup. But you can also navigate and scroll
            // through your most important UI.
            startActivityAndWait()
            device.waitForIdle()

            device.run {
                findObject(By.text("Interests"))
                    .click()
                waitForIdle()
                findObject(By.text("Accessibility")).scroll(Direction.DOWN, 2000f)
                waitForIdle()
                findObject(By.text("People")).click()
                waitForIdle()
                findObject(By.textStartsWith("Android")).scroll(Direction.DOWN, 2000f)
                waitForIdle()
            }
        }
}
