package ci.nsu.mobile.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {

    // Поля формы
    private val _firstName = MutableStateFlow("")
    val firstName: StateFlow<String> = _firstName.asStateFlow()

    private val _lastName = MutableStateFlow("")
    val lastName: StateFlow<String> = _lastName.asStateFlow()

    private val _middleName = MutableStateFlow("")
    val middleName: StateFlow<String> = _middleName.asStateFlow()

    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> = _birthDate.asStateFlow()

    private val _gender = MutableStateFlow("")
    val gender: StateFlow<String> = _gender.asStateFlow()

    // TODO: Выбор группы из доступных (получить через getGroups)
    private val _groupId = MutableStateFlow<Int?>(null)
    val groupId: StateFlow<Int?> = _groupId.asStateFlow()

    private val _login = MutableStateFlow("")
    val login: StateFlow<String> = _login.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    // Группы для выпадающего списка (пока заглушка)
    private val _groups = MutableStateFlow(listOf(
        Group(1, "Группа A"),
        Group(2, "Группа B"),
        Group(3, "Группа C")
    ))
    val groups: StateFlow<List<Group>> = _groups.asStateFlow()

    // Методы обновления
    fun updateFirstName(value: String) { _firstName.update { value } }
    fun updateLastName(value: String) { _lastName.update { value } }
    fun updateMiddleName(value: String) { _middleName.update { value } }
    fun updateBirthDate(value: String) { _birthDate.update { value } }
    fun updateGender(value: String) { _gender.update { value } }
    fun updateGroupId(value: Int?) { _groupId.update { value } }
    fun updateLogin(value: String) { _login.update { value } }
    fun updatePassword(value: String) { _password.update { value } }
    fun updateEmail(value: String) { _email.update { value } }
    fun updatePhoneNumber(value: String) { _phoneNumber.update { value } }

    fun onRegisterClick() {
        // TODO: реализовать регистрацию
    }
}

data class Group(
    val id: Int,
    val name: String
)