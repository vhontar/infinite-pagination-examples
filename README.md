# pagination-examples

pagination-example is an project where were implemented several types of adapter for recycler view with eternal pagination.

## RecyclerView.Adapter (presentation/adapters/v1)

A basic adapter which inherits from ```RecyclerView.Adapter``` abstract class
It contains ```PaginationScrollListener``` which helps to understand where we need to ask for more data or stop pagination.

```
class PaginationScrollListener(
    private val adapter: PagingAdapter,
    private val loadMore: () -> Unit
): RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!adapter.fullyLoaded && !adapter.loading && isLastItem(layoutManager)) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount > 0) {
                adapter.showLoading()
                loadMore.invoke()
            }
        }
    }

    private fun isLastItem(layoutManager: LinearLayoutManager): Boolean {
        val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
        return lastVisiblePosition == adapter.itemCount - 1
    }
}
```
Created an abstract class PagingAdapter which can be reused for different lists with different models
```
abstract class PagingAdapter(
    private val items: ArrayList<Holder> = arrayListOf(),
    private val defaultItemsCount: Int = AppConstants.RECORD_LIMIT
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var fullyLoaded: Boolean = false
        private set
    var loading: Boolean = false
        private set
    private var loadMoreModeEnabled: Boolean = false

    val currentList: List<Holder>
        get() = items

    override fun getItemCount(): Int = items.size
    
    ...
}
```
```PagingAdapter``` provides a ```pushList``` method for adding list of data to adapter and then to recycler view
```
fun pushList(holders: List<Holder>) {
    if (loadMoreModeEnabled) {
        hideLoading()

        if (holders.isEmpty()) {
            fullyLoaded = true
            return
        }

        if (holders.size < defaultItemsCount) {
            fullyLoaded = true
        }

        val startPosition = items.size
        items.addAll(holders)
        notifyItemRangeInserted(startPosition, holders.size)
    } else {
        items.clear()
        items.addAll(holders)
        notifyDataSetChanged()
    }
}
```
Implementation of v1 adapter inside activity/fragment 
```
private fun implementV1Adapter() {
    binding.rvRecipes.adapter = adapterV1
    binding.rvRecipes.addOnScrollListener(PaginationScrollListenerV1(adapterV1) {
       viewModel.fetchRecipes()
    })

    viewModel.fetchRecipes()
    viewModel.recipes.observe(this) {
       adapterV1.pushList(it)
    }
}
```

## ListAdapter (presentation/adapters/v2)

An adapter which inherits from ListAdapter
Paginated ```ListAdapter``` is similar to RecyclerView.Adapter implementation with several differences.

We don't need ```private val items: ArrayList<Holder> = arrayListOf()``` inside PagingAdapter constructor, because ListAdapter was already implemented it as ```currentList``` param.
```
abstract class PagingAdapter(
    private val defaultItemsCount: Int = AppConstants.RECORD_LIMIT
) : ListAdapter<Holder, RecyclerView.ViewHolder>(PagingHolderDiffUtilCallback()) {
```
```pushList``` was changed too, because ```ListAdapter.submitList``` runs in on background thread. Two sequential calls might cancel the first one.
```
fun pushList(holders: List<Holder>) {
    if (loadMoreModeEnabled) {
        loading = false

        if (holders.isEmpty()) {
            fullyLoaded = true
            return
        }

        if (holders.size < defaultItemsCount) {
            fullyLoaded = true
        }

        val newList = currentList.copy()
        if (newList.isNotEmpty() && newList[newList.size - 1] is ProgressBarHolder) {
            newList.removeAt(newList.size - 1)
        }
        newList.addAll(holders)
        submitList(newList)
    } else {
       submitList(holders)
    }
}
```
## PagingAdapter (presentation/adapters/v3)

Implmenentation of Paging3 library, which is the easiest way to implmenent adapter for recycler view
```
class RecipePagingSource(
    private val recipeNetwork: RecipeNetwork,
    private val query: String
) : PagingSource<Int, Recipe>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        val skip = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = recipeNetwork.fetchRecipes(query, skip, skip + AppConstants.RECORD_LIMIT)
            LoadResult.Page(
                data = response,
                prevKey = if (skip == DEFAULT_PAGE_INDEX) null else skip - AppConstants.RECORD_LIMIT,
                nextKey = if (response.isEmpty()) null else skip + AppConstants.RECORD_LIMIT
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? = null

    companion object {
        private const val DEFAULT_PAGE_INDEX = 0
    }
}
```
```RecipeAdapter``` inherits ```PagingDataAdapter``` 
```
class RecipeAdapter: PagingDataAdapter<Recipe, RecipeViewHolder>(RecipeDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder = RecipeViewHolder.from(parent)
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) = holder.bind(getItem(position))
}

class RecipeDiffUtilCallback : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean = oldItem.imageUrl == newItem.imageUrl
    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean = oldItem == newItem
}
```
Implementation of ```RecipeAdapter``` inside activity/fragment
```
private fun implementV3Adapter() {
    val loaderStateAdapter = RecipeLoaderStateAdapter { adapterV3.retry() }
    binding.rvRecipes.adapter = adapterV3.withLoadStateFooter(loaderStateAdapter)
    lifecycleScope.launch {
        viewModel.fetchRecipesForPagingAdapter().distinctUntilChanged().collectLatest {
        if (viewModel.dataLoading.value == true)
             viewModel.setDataLoading(false)
             adapterV3.submitData(it)
         }
    }
}
```
