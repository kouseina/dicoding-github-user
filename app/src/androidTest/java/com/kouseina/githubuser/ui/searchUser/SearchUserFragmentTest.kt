package com.kouseina.githubuser.ui.searchUser

import android.view.KeyEvent
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.typeText
import org.hamcrest.Matchers.allOf
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kouseina.githubuser.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchUserFragmentTest {
    private lateinit var scenario: FragmentScenario<SearchUserFragment>

    @Before
    fun setup(){
        val value = "0.0"
//        InstrumentationRegistry.getInstrumentation().uiAutomation.run {
//            this.executeShellCommand("settings put global $WINDOW_ANIMATION_SCALE $value")
//            this.executeShellCommand("settings put global $TRANSITION_ANIMATION_SCALE $value")
//            this.executeShellCommand("settings put global $ANIMATOR_DURATION_SCALE $value")
//        }

        scenario = launchFragmentInContainer(themeResId = R.style.Theme_GithubUser)
        scenario.moveToState(Lifecycle.State.STARTED)
    }
    @Test
    fun testSearchUser() {
        val username = "kouseina"

        onView(withId(R.id.searchBar)).perform(click())
        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.searchView))))
            .perform(typeText(username)).perform(pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.rv_user)).check(matches(isDisplayed()))
    }
}