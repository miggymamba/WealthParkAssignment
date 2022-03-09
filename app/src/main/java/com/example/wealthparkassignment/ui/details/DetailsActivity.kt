package com.example.wealthparkassignment.ui.details

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.wealthparkassignment.R
import com.example.wealthparkassignment.base.BaseActivity
import com.example.wealthparkassignment.databinding.ActivityDetailsBinding
import com.example.wealthparkassignment.util.Constants
import com.example.wealthparkassignment.util.ViewModelFactory
import com.example.wealthparkassignment.util.extensions.injectViewModel
import com.example.wealthparkassignment.util.extensions.observe
import com.example.wealthparkassignment.util.extensions.toast
import com.example.wealthparkassignment.util.single_live_event.LiveEvent
import javax.inject.Inject

class DetailsActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        init()
        setObservers()
    }

    fun init() {
        viewModel = injectViewModel(viewModelFactory)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        viewModel.initQuery(
            type = intent?.extras?.getString(Constants.INTENT_KEY_TYPE)!!,
            name = intent?.extras?.getString(Constants.INTENT_KEY_NAME)!!
        )
    }

    fun setObservers() {
        observe(viewModel.statusQuery) {
            if (it is LiveEvent.Error) {
                toast(it.errorMessage)
            }
        }
    }
}