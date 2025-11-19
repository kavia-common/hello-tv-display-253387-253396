package com.example.hello_tv_frontend

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.ViewFlipper
import android.animation.ObjectAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.os.Build
import android.graphics.Color
import android.view.ViewGroup

/**
 * Activity that shows the Ocean Professional splash screen for 5 seconds, then launches MainActivity.
 * Designed for Android TV: centered, large typography, Ocean Professional color palette, TV safe margins.
 */
class SplashActivity : Activity() {

    private val splashDuration = 5000L // milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create root FrameLayout for TV safe area
        val root = FrameLayout(this).apply {
            setBackgroundDrawable(createSplashGradient())
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
            )
        }

        // Centered ViewFlipper to allow future fade animations
        val flipper = ViewFlipper(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
            )
            isFocusable = false
        }

        // Hello TV Title
        val title = TextView(this).apply {
            text = "Hello TV"
            textSize = 48f
            setTextColor(Color.parseColor("#111827")) // Text: Ocean Professional
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 0)
            setTypeface(typeface, android.graphics.Typeface.BOLD)
        }

        // Accent underline (small View, centered under title)
        val underline = View(this).apply {
            val accentColor = Color.parseColor("#F59E0B")
            layoutParams = FrameLayout.LayoutParams(
                dpToPx(72), // width
                dpToPx(6)   // height
            ).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                topMargin = dpToPx(8)
            }
            setBackgroundColor(accentColor)
        }

        // Container (vertical stack: title then underline, both centered)
        val verticalLayout = FrameLayout(this).apply {
            // TV safe area (margins to avoid overscan)
            setPadding(dpToPx(48), dpToPx(48), dpToPx(48), dpToPx(48))
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
            )
            // For accessibility: set content description
            contentDescription = "Splash screen"
        }

        // Position title and underline vertically
        val lpTitle = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT
        )
        lpTitle.gravity = Gravity.CENTER
        verticalLayout.addView(title, lpTitle)

        val lpUnderline = FrameLayout.LayoutParams(
            dpToPx(72), dpToPx(6)
        )
        lpUnderline.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
        lpUnderline.topMargin = dpToPx(56)
        verticalLayout.addView(underline, lpUnderline)

        // Add the vertical stack to the full screen flipper
        flipper.addView(verticalLayout)
        root.addView(flipper)

        setContentView(root)

        // Optional: fade in animation (subtle)
        root.alpha = 0f
        ObjectAnimator.ofFloat(root, "alpha", 0f, 1f).apply {
            duration = 700L
            interpolator = AccelerateDecelerateInterpolator()
        }.start()

        // Delay, then fade out and proceed to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Subtle fade-out
            root.animate().alpha(0f).setDuration(700L).withEndAction {
                // Launch HomeActivity, finish Splash so it's not in back stack
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }.start()
        }, splashDuration)
    }

    /**
     * Creates a subtle vertical gradient: Ocean blue to background (Ocean Professional)
     */
    private fun createSplashGradient(): GradientDrawable {
        val primary = Color.parseColor("#2563EB") // ocean blue
        val background = Color.parseColor("#f9fafb") // from theme
        // 10% blue —> near white
        val startColor = Color.argb(25, Color.red(primary), Color.green(primary), Color.blue(primary))
        val endColor = background
        return GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(startColor, endColor)
        )
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
