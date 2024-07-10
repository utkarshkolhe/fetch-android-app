package com.example.fetchlistapp.data


import android.util.Log
import com.example.fetchlistapp.R
import org.json.JSONArray

data class ListGroup(
    var listItems:List<ListItem>,
    var searchTerm:String,
    var sortSequence:String
){
    fun getSortedListItems(): List<ListItem> {
        var sortedListItems = mutableListOf<ListItem>()
        for (listItem in listItems) {
            if (searchTerm in listItem.id.toString().lowercase() || searchTerm in listItem.listId.toString().lowercase() || searchTerm in listItem.name.lowercase()){
                sortedListItems.add(listItem)
            }
        }
        if (sortSequence=="sort_listid_asc"){
            sortedListItems.sortBy { it.listId }
        }
        if (sortSequence=="sort_listid_dsc"){
            sortedListItems.sortByDescending  { it.listId }
        }
        if (sortSequence=="sort_name_asc"){
            sortedListItems.sortBy { it.name }
        }
        if (sortSequence=="sort_name_dsc"){
            sortedListItems.sortByDescending  { it.name }
        }

        return sortedListItems
    }
    companion object {
        fun fromJsonArray(jsonArray: JSONArray): ListGroup {
            val listItems = mutableListOf<ListItem>()
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val listItem = ListItem.fromJson(jsonObject)
                if (!listItem.name.isNullOrEmpty() && listItem.name!="null"){
                    listItems.add(listItem)
                }
            }
            return ListGroup(listItems=listItems,searchTerm="",sortSequence="")
        }
    }
}
