package com.kouseina.githubuser.ui.favoriteUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kouseina.githubuser.data.database.FavoriteUser
import com.kouseina.githubuser.databinding.FragmentFavoriteUserBinding
import com.kouseina.githubuser.ui.ViewModelFactory

class FavoriteUserFragment : Fragment() {
    private var _binding: FragmentFavoriteUserBinding? = null
    private val binding get() = _binding!!

    companion object {
//        const val TAG = "FavoriteUserFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteUserBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(view.context)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(view.context, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        val viewModel = obtainViewModel(requireActivity())
        viewModel.getAll().observe(viewLifecycleOwner) {
            if (it != null) {
                setFavoriteUserListData(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun obtainViewModel(activity: FragmentActivity): FavoriteUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUserViewModel::class.java]
    }

    private fun setFavoriteUserListData(userList: List<FavoriteUser>) {
        val adapter = FavoriteUserAdapter()
        adapter.submitList(userList)
        binding.rvUser.adapter = adapter
    }
}