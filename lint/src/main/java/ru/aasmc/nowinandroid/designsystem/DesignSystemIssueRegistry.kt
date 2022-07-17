package ru.aasmc.nowinandroid.designsystem

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

/**
 * An issue registry that checks for incorrect usages of Compose Material APIs over equivalents in
 * the Now in Android design system module.
 */
@Suppress("UnstableApiUsage")
class DesignSystemIssueRegistry : IssueRegistry(){
    override val issues: List<Issue> = listOf(DesignSystemDetector.ISSUE)

    override val api: Int = CURRENT_API

    override val minApi: Int = 12

    override val vendor: Vendor = Vendor(
        vendorName = "Now in Android",
        feedbackUrl = "https://github.com/android/nowinandroid/issues",
        contact = "https://github.com/android/nowinandroid"
    )
}