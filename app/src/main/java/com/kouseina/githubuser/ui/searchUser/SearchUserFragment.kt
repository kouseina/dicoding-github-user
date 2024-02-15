package com.kouseina.githubuser.ui.searchUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kouseina.githubuser.R
import com.kouseina.githubuser.data.database.SettingPreferences
import com.kouseina.githubuser.data.database.dataStore
import com.kouseina.githubuser.data.response.ItemsItem
import com.kouseina.githubuser.databinding.FragmentSearchUserBinding
import com.kouseina.githubuser.ui.setting.SettingViewModel
import com.kouseina.githubuser.ui.setting.SettingViewModelFactory


class SearchUserFragment : Fragment() {
    private var _binding: FragmentSearchUserBinding? = null
    private val binding get() = _binding!!

    private val searchUserViewModel by viewModels<SearchUserViewModel>()

    companion object {
        const val TAG = "SearchUserFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchUserBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTheme()

        binding.searchBar.inflateMenu(R.menu.menu)
        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            val navController = findNavController(requireActivity(), R.id.container)

            when (menuItem.itemId) {
                R.id.menu_favorite -> {
                    val toFavoriteUserFragment = SearchUserFragmentDirections.actionSearchUserFragmentToFavoriteUserFragment()
                    navController.navigate(toFavoriteUserFragment)

                    true
                }
                R.id.menu_setting -> {
                    val toSettingFragment = SearchUserFragmentDirections.actionSearchUserFragmentToSettingFragment()
                    navController.navigate(toSettingFragment)

                    true
                }
                else -> false
            }
        }

        val layoutManager = LinearLayoutManager(view.context)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(view.context, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        searchUserViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        searchUserViewModel.userList.observe(viewLifecycleOwner) {
            setUserListData(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()

                    val searchText = searchView.text.toString()

                    val q = searchText.ifBlank { "daffa" }

                    searchUserViewModel.fetchSearchUser(q)

                    false
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setUserListData(userList: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(userList)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            binding.progressCircular.visibility = View.GONE
        }
    }

    private fun setTheme() {
        val pref = SettingPreferences.getInstance(requireActivity().dataStore)
        val mainViewModel =
            ViewModelProvider(this, SettingViewModelFactory(pref))[SettingViewModel::class.java]
        mainViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}