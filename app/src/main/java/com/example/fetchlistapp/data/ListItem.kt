package com.example.fetchlistapp.data
import org.json.JSONObject

data class ListItem(
    val id: Int,
    val listId: Int,
    val name: String
){
    companion object {
        fun fromJson(jsonObject: JSONObject): ListItem {
            return ListItem(
                id = jsonObject.getInt("id"),
                listId = jsonObject.getInt("listId"),
                name = jsonObject.getString("name")
            )
        }
    }
}
