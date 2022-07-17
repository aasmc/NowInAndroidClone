package ru.aasmc.nowinandroid.designsystem

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UQualifiedReferenceExpression

/**
 * A detector that checks for incorrect usages of Compose Material APIs over equivalents in
 * the Now in Android design system module.
 */
@Suppress("UnstableApiUsage")
class DesignSystemDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>>? {
        return listOf(
            UCallExpression::class.java,
            UQualifiedReferenceExpression::class.java
        )
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitCallExpression(node: UCallExpression) {
                val name = node.methodName ?: return
                val preferredName = METHOD_NAMES[name] ?: return
                reportIssue(context, node, name, preferredName)
            }

            override fun visitQualifiedReferenceExpression(node: UQualifiedReferenceExpression) {
                val name = node.receiver.asRenderString()
                val preferredName = RECEIVER_NAMES[name] ?: return
                reportIssue(context, node, name, preferredName)
            }
        }
    }

    companion object {
        @JvmField
        val ISSUE: Issue = Issue.create(
            id = "DesignSystem",
            briefDescription = "Design system",
            explanation = "This check highlights calls in code that use Compose Material " +
                    "composables instead of equivalents from the Now in Android design system " +
                    "module.",
            category = Category.CUSTOM_LINT_CHECKS,
            priority = 7,
            severity = Severity.ERROR,
            implementation = Implementation(
                DesignSystemDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )

        // Unfortunately :lint is a Java module and thus can't depend on the :core-designsystem
        // Android module, so we can't use composable function references (eg. ::Button.name)
        // instead of hardcoded names.
        val METHOD_NAMES = mapOf(
            "MaterialTheme" to "NiaTheme",
            "Button" to "NiaFilledButton",
            "OutlinedButton" to "NiaOutlinedButton",
            "TextButton" to "NiaTextButton",
            "FilterChip" to "NiaFilterChip",
            "ElevatedFilterChip" to "NiaFilterChip",
            "DropdownMenu" to "NiaDropdownMenu",
            "NavigationBar" to "NiaNavigationBar",
            "NavigationBarItem" to "NiaNavigationBarItem",
            "NavigationRail" to "NiaNavigationRail",
            "NavigationRailItem" to "NiaNavigationRailItem",
            "TabRow" to "NiaTabRow",
            "Tab" to "NiaTab",
            "IconToggleButton" to "NiaToggleButton",
            "FilledIconToggleButton" to "NiaToggleButton",
            "FilledTonalIconToggleButton" to "NiaToggleButton",
            "OutlinedIconToggleButton" to "NiaToggleButton",
            "CenterAlignedTopAppBar" to "NiaTopAppBar",
            "SmallTopAppBar" to "NiaTopAppBar",
            "MediumTopAppBar" to "NiaTopAppBar",
            "LargeTopAppBar" to "NiaTopAppBar"
        )

        val RECEIVER_NAMES = mapOf(
            "Icons" to "NiaIcons"
        )

        fun reportIssue(
            context: JavaContext,
            node: UElement,
            name: String,
            preferredName: String
        ) {
            context.report(
                ISSUE, node, context.getLocation(node),
                "Using $name instead of $preferredName"
            )
        }
    }
}