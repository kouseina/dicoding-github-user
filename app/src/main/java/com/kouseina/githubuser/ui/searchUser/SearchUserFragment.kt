package com.kouseina.githubuser.ui.searchUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kouseina.githubuser.R
import com.kouseina.githubuser.data.response.ItemsItem
import com.kouseina.githubuser.databinding.FragmentSearchUserBinding


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

        binding.searchBar.inflateMenu(R.menu.menu)
        binding.searchBar.setOnMenuItemClickListener { menuItem ->
             when (menuItem.itemId) {
                R.id.menu_favorite -> {
                    val toFavoriteUserFragment = SearchUserFragmentDirections.actionSearchUserFragmentToFavoriteUserFragment()
                    val navController = findNavController(requireActivity(), R.id.container)
                    navController.navigate(toFavoriteUserFragment)
                    true
                }
                R.id.menu_setting -> {
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
}