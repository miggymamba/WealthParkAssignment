package com.example.wealthparkassignment.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wealthparkassignment.R
import com.example.wealthparkassignment.base.BaseActivity
import com.example.wealthparkassignment.data.model.GetCitiesResponse
import com.example.wealthparkassignment.data.model.GetFoodsResponse
import com.example.wealthparkassignment.databinding.ActivityMainBinding
import com.example.wealthparkassignment.ui.adapters.CitiesAdapter
import com.example.wealthparkassignment.ui.adapters.FoodsAdapter
import com.example.wealthparkassignment.ui.adapters.HeaderAdapter
import com.example.wealthparkassignment.ui.details.DetailsActivity
import com.example.wealthparkassignment.util.Constants
import com.example.wealthparkassignment.util.DialogUtil
import com.example.wealthparkassignment.util.ViewModelFactory
import com.example.wealthparkassignment.util.extensions.injectViewModel
import com.example.wealthparkassignment.util.extensions.observe
import com.example.wealthparkassignment.util.extensions.startActivity
import com.example.wealthparkassignment.util.single_live_event.LiveEvent
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var citiesAdapter: CitiesAdapter
    private lateinit var foodsAdapter: FoodsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
        setObservers()
    }

    private fun init() {
        viewModel = injectViewModel(viewModelFactory)
        binding.lifecycleOwner = this
        citiesAdapter = CitiesAdapter(GetCitiesResponse(), onCityClicked = {})
        foodsAdapter = FoodsAdapter(GetFoodsResponse(), onFoodClicked = {})

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshData()
    }

    private fun setObservers() {
        observe(viewModel.pairMediatorLiveData){
            if(it != null){
                val isCitiesNullEmpty = it.first.isNullOrEmpty()
                val isFoodsNullEmpty = it.second.isNullOrEmpty()

                if(!isCitiesNullEmpty) citiesAdapter = CitiesAdapter(it.first!!, onCityClicked = {
                    startActivity<DetailsActivity> {
                        putExtra(Constants.INTENT_KEY_TYPE,Constants.INTENT_TYPE_CITY)
                        putExtra(Constants.INTENT_KEY_NAME,it.name)
                    }
                })
                if(!isFoodsNullEmpty) foodsAdapter = FoodsAdapter(it.second!!, onFoodClicked = {
                    startActivity<DetailsActivity> {
                        putExtra(Constants.INTENT_KEY_TYPE,Constants.INTENT_TYPE_FOOD)
                        putExtra(Constants.INTENT_KEY_NAME,"miniso")
                    }
                })

                if(!isCitiesNullEmpty && !isFoodsNullEmpty){
                    if(binding.swipeRefresh.isRefreshing) binding.swipeRefresh.isRefreshing = false
                    DialogUtil.instance.dismissAlertDialog()
                    DialogUtil.instance.dismissLoadingAlertDialog()
                    binding.rvDetails.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = ConcatAdapter(
                            HeaderAdapter(getString(R.string.cities_header)),citiesAdapter, HeaderAdapter(getString(R.string.foods_header))
                            ,foodsAdapter)
                    }
                }

                if(isCitiesNullEmpty && isFoodsNullEmpty){
                    DialogUtil.instance.showAlertDialogMessageOnly(this,getString(R.string.error_fetching_data))
                }
            }else{
                DialogUtil.instance.showLoadingAlertDialog(this)
            }

        }

        observe(viewModel.statusGetCities){
            when(it){
                is LiveEvent.Error -> {
                    DialogUtil.instance.dismissLoadingAlertDialog()
                    if(binding.swipeRefresh.isRefreshing) binding.swipeRefresh.isRefreshing = false
                }
            }
        }

        observe(viewModel.statusGetFoods){
            when(it){
                is LiveEvent.Error -> {
                    DialogUtil.instance.dismissLoadingAlertDialog()
                    if(binding.swipeRefresh.isRefreshing) binding.swipeRefresh.isRefreshing = false
                }
            }
        }
    }
}