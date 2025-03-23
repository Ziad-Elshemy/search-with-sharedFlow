package eg.iti.mad.sharedandstateflow.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _searchFlow = MutableSharedFlow<String>(replay = 1)

    private val allNames = listOf(
        "Ahmed Ali", "Mohamed Hassan", "Omar Mahmoud", "Youssef Tarek", "Khaled Saad",
        "Mostafa Ehab", "Hassan Nabil", "Karim Sherif", "Ibrahim Adel", "Tamer Younis",
        "Abdallah Hisham", "Samir Magdy", "Hany Gamal", "Ehab Reda", "Nader Fathy",
        "Waleed Sayed", "Sherif Ashraf", "Amr Zaki", "Tarek Lotfy", "Bassem Salah",
        "Ramy Nasser", "Wael Mamdouh", "Ashraf Helmy", "Ayman Galal", "Hesham Anwar",
        "Mina Hany", "Mahmoud Refaat", "Sayed Mostafa", "Gamal Ibrahim", "Yassin Osama"
    )

    fun updateQuery(query: String) {
        _searchQuery.value = query
        viewModelScope.launch { _searchFlow.emit(query) }
    }

    val filteredNames = _searchFlow
        .debounce(300)
        .distinctUntilChanged()
        .mapLatest { query -> //from shared flow
            if (query.isEmpty()) emptyList()
            else allNames.filter { it.startsWith(query, ignoreCase = true) }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


}