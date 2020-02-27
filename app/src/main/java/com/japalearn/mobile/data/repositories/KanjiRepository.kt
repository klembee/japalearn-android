package com.japalearn.mobile.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.japalearn.mobile.data.models.learning.Kanji
import com.japalearn.mobile.data.models.KanjiCategory
import com.japalearn.mobile.data.datasources.KanjiDataSource
import com.magestionplus.android.data.database.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class KanjiRepository(
    val localDataSource: AppDatabase,
    val cloudDatasource: KanjiDataSource
) {

    /**
     * Get the kanji categories with their kanjis
     */
    fun getCategories(): LiveData<List<KanjiCategory>> {
        val data = MutableLiveData<List<KanjiCategory>>()

        cloudDatasource.getCategories {
            if(it.success){
                data.postValue(it.categories)
            }
        }

        return data
    }

    /**
     * Get the list of kanjis of a certain category from the database
     */
    fun getKanjisOfCategory(category: String): LiveData<List<Kanji>>{
        val data = MutableLiveData<List<Kanji>>()

        cloudDatasource.getKanjisInCategory(category){
            if(it.success){
                data.postValue(it.kanjis)
            }
        }

        return data
    }

    fun getKanji(id: Int): LiveData<Kanji>{
        val data = MutableLiveData<Kanji>()

        cloudDatasource.getKanji(id){
            if(it.success){
                data.postValue(it.kanjis[0])
            }
        }

        return data
    }

    private fun getFromDataBase(category: String? = null): LiveData<List<Kanji>>{
        val data = MutableLiveData<List<Kanji>>()

        GlobalScope.launch {
            if(category != null){
                data.postValue(localDataSource.kanjiDao().getOfCategory(category))
            }else {
                data.postValue(localDataSource.kanjiDao().get())
            }
        }

        return data
    }

    private fun addToDatabase(kanjis: List<Kanji>){
        GlobalScope.launch {
            kanjis.forEach {
                localDataSource.kanjiDao().insert(it)
            }
        }
    }

//    /**
//     * Converts a list of kanjis to a list of kanji categories
//     */
//    private fun kanjiListToCategoryList(kanjis: List<Kanji>?): List<KanjiCategory>{
//        val categories = HashMap<String, ArrayList<Kanji>>()
//        kanjis?.let{
//            kanjis.forEach {kanji ->
//                if(!categories.containsKey(kanji.category)){
//                    categories.put(kanji.category, ArrayList())
//                }
//                val list = categories[kanji.category]
//
//                list?.let {
//                    list.add(kanji)
//                    categories.put(kanji.category, it)
//                }
//            }
//        }
//
//        val categoryList = ArrayList<KanjiCategory>()
//        categories.keys.forEach {
//            categoryList.add(KanjiCategory(it, categories[it]?.toList() ?: emptyList()))
//        }
//
//        return categoryList
//    }

    companion object{
        fun instantiate(context: Context?): KanjiRepository?{
            context?.let {
                return KanjiRepository(AppDatabase.invoke(it), KanjiDataSource())
            }
            return null
        }
    }
}