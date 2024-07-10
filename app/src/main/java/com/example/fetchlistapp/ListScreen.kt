package com.example.fetchlistapp

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fetchlistapp.data.ListGroup
import com.example.fetchlistapp.data.ListItem
import com.example.fetchlistapp.utils.DataFetcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray


class ListScreen : AppCompatActivity() {
    private lateinit var listContainer: LinearLayout
    private lateinit var searchView: SearchView
    private  var listGroup=ListGroup(listOf<ListItem>(),"","")
    private var sortbuttons=mutableListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        listContainer = findViewById(R.id.ll_listcontainer)
        searchView = findViewById<SearchView>(R.id.searchView)

        // Get Data URL from strings
        val url = getString(R.string.data_url)

        //Fetch data and inflate Scrollview
        fetchJsonData(url)

        //Inflate Sort buttons
        inflateSortButton()
        //Set listener on search bar
        setSearchViewListener()
    }
    private fun setSearchViewListener(){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search action
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text change
                listGroup.searchTerm=newText.toString()
                inflateContainer()
                return true
            }
        })
    }

    private fun inflateContainer(){
        val inflater = LayoutInflater.from(this)
        val listItems = listGroup.getSortedListItems()
        listContainer.removeAllViews()
        for (listItem in listItems) {
            val itemView = inflater.inflate(R.layout.list_item_layout, listContainer, false)

            val textViewID = itemView.findViewById<TextView>(R.id.tv_item_id)
            val textViewListID = itemView.findViewById<TextView>(R.id.tv_item_listid)
            val textViewName = itemView.findViewById<TextView>(R.id.tv_item_name)
            val idText= getString(R.string.id_desc)+" "+ listItem.id.toString()
            textViewID.text = idText
            textViewListID.text = listItem.listId.toString()
            textViewName.text = listItem.name

            listContainer.addView(itemView)
        }
    }

    private fun fetchJsonData(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = DataFetcher.fetchDataFromUrl(url)
            withContext(Dispatchers.Main) {
                if (result != null) {
                    try {
                        val jsonArray = JSONArray(result)
                        listGroup = ListGroup.fromJsonArray(jsonArray)
                        inflateContainer()
                    } catch (e: Exception) {
                        inflateError(getString(R.string.error_parsingdata))
                    }
                } else {
                    inflateError(getString(R.string.error_fetchdata))
                }
            }
        }
    }
    private fun inflateSortButton(){
        val linearLayout = findViewById<LinearLayout>(R.id.ll_sort_buttons)
        val inflater = LayoutInflater.from(this)
        val stringArray = arrayOf(getString(R.string.sort_listid_asc), getString(R.string.sort_listid_dsc), getString(R.string.sort_name_asc), getString(R.string.sort_name_dsc))
        val sortTexts= arrayOf("sort_listid_asc","sort_listid_dsc", "sort_name_asc","sort_name_dsc")
        for (i in 0..3) {
            val buttonContainer = inflater.inflate(R.layout.sort_button_layout, linearLayout, false)
            val button = buttonContainer.findViewById<Button>(R.id.btn_sort)
            val sortCode=sortTexts[i]
            button.apply {
                    val btnText= "  "+stringArray[i]+"  "
                    text =btnText
                    setOnClickListener {
                        // Handle button click

                        //Remove tint from other sort buttons
                        removeTint()

                        //Set sort sequence
                        listGroup.sortSequence= sortCode

                        // Add tint to active button
                        val drawable = DrawableCompat.wrap(button.background)
                        DrawableCompat.setTint(drawable, ContextCompat.getColor(this@ListScreen, R.color.orangetint))

                        //Inflate with sorted list
                        inflateContainer()
                    }
                }
            sortbuttons.add(button)
            linearLayout.addView(buttonContainer)
        }
    }

    private fun removeTint(){
        for (button in sortbuttons){
            val drawable = DrawableCompat.wrap(button.background)
            DrawableCompat.setTintList(drawable, null)

        }
    }
    private fun inflateError(errorText:String){
        val inflater = LayoutInflater.from(this)
        listContainer.removeAllViews()
        val itemView = inflater.inflate(R.layout.error_layout, listContainer, false)
        val textViewID = itemView.findViewById<TextView>(R.id.tv_error)
        textViewID.text = errorText
        listContainer.addView(itemView)
    }


}