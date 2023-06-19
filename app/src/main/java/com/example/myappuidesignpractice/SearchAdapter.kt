package com.example.myappuidesignpractice
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.myappuidesignpractice.databinding.PostItemBinding


class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(), Filterable{
    private lateinit var binding : PostItemBinding
    var searchData = ArrayList<PostData>()
    private lateinit var context : Context
    var filterPost = ArrayList<PostData>()
    init {
        setHasStableIds(true)
    }

    inner class SearchViewHolder(private val binding : PostItemBinding ) : RecyclerView.ViewHolder(binding.root) {
        private var position : Int? = null
        var accountId = binding.accountId
        var accountProfile = binding.accountImage
        var postContent = binding.postContentText
        fun bind(accountData: PostData, position : Int) {
            this.position = position
            accountId.text = accountData.accountID
            postContent.text = accountData.postContent

            //accountProfile.setImageURI() = pillData.pillTakeTime

            binding.root.setOnClickListener {
                itemClickListener.onClick(it, layoutPosition, searchData[layoutPosition].postID)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        context = parent.context
        binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(searchData[holder.adapterPosition], holder.adapterPosition)
        /*binding.homeRemoveIv.setOnClickListener {
            val builder : AlertDialog.Builder = AlertDialog.Builder(context)
            val ad : AlertDialog = builder.create()
            var deleteData = pillItemData[holder.adapterPosition]!!.pillName
            builder.setTitle(deleteData)
            builder.setMessage("정말로 삭제하시겠습니까?")
            builder.setNegativeButton("예",
                DialogInterface.OnClickListener { dialog, which ->
                    ad.dismiss()
                    //temp = listData[holder.adapterPosition]!!
                    //extraditeData()
                    //testData.add(temp)
                    //deleteServerData = tempServerData[holder.adapterPosition]!!.api_id
                    removeData(holder.adapterPosition)
                    //removeServerData(deleteServerData!!)
                    //println(deleteServerData)
                })

            builder.setPositiveButton("아니오",
                DialogInterface.OnClickListener { dialog, which ->
                    ad.dismiss()
                })
            builder.show()
        }*/
    }

    override fun getItemCount(): Int {
        return searchData.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    //데이터 Handle 함수
    fun removeData(position: Int) {
        searchData.removeAt(position)
        //temp = null
        notifyItemRemoved(position)
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int, itemId: Int)
    }

    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun getFilter(): Filter {
        return PostFilter()
    }
    inner class PostFilter : Filter() {
        // 입력받은 문자열에 대한 처리
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filterString = constraint.toString()
            val results = FilterResults()

            //검색이 필요없을 경우를 위해 원본배열 복제
            val filterList : ArrayList<PostData> = ArrayList()

            //공벡제외 아무런 값도 입력하지 않았을 경우 ->원본배열
            if (filterString.trim { it <= ' '}.isEmpty()) {
                //필터링 작업으로 계산된 모든 값
                results.values = searchData
                //필터링 작업으로 계산된 값의 수
                results.count = searchData.size
                return results

                //20글자 수 이하일 때 -> 이메일로 검색
            } else if (filterString.trim {it <= ' '}.length <= 20) {
                for (searchEmail in searchData) {
                    if (searchEmail.postContent.contains(filterString)) {
                        filterList.add(searchEmail)
                    }
                }
            }
            results.values = filterList
            results.count = filterList.size

            return results
        }

        //처리에 대한 결과물
        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            filterPost.clear()
            filterPost.addAll(results.values as ArrayList<PostData>)
            notifyDataSetChanged()
        }
    }
}