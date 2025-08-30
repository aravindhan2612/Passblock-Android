package com.ab.an.data.network.impl

import app.cash.turbine.test
import com.ab.an.core.utils.Resource
import com.ab.an.data.impl.UserApiRepositoryImpl
import com.ab.an.data.mapper.toUser
import com.ab.an.data.mapper.toUserDto
import com.ab.an.data.network.api.UserApiService
import com.ab.an.data.network.dto.AuthResponseDto
import com.ab.an.data.network.dto.UserDto
import com.ab.an.data.utils.ErrorResponse
import com.ab.an.domain.model.User
import com.ab.an.domain.repository.AppSettingsDataStoreRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class UserApiRepositoryImplTest {

    private lateinit var apiService: UserApiService
    private lateinit var appSettingsDataStoreRepository: AppSettingsDataStoreRepository
    private lateinit var userApiRepositoryImpl: UserApiRepositoryImpl

    @Before
    fun setup() {
        apiService = mockk()
        appSettingsDataStoreRepository = mockk(relaxed = true)
        userApiRepositoryImpl = UserApiRepositoryImpl(apiService, appSettingsDataStoreRepository)
    }

    @Test
    fun `test register api call with return success`() = runTest {
        coEvery { apiService.register(mockUser.toUserDto()) } returns Response.success(response)
        userApiRepositoryImpl.register(mockUser).test {
            val loadingState = awaitItem()
            Assert.assertTrue(
                "Expected Resource.Loading but was ${loadingState::class.simpleName}",
                loadingState is Resource.Loading
            )
            val successResource = awaitItem()
            Assert.assertTrue(
                "Expected Resource.Success but was ${successResource::class.simpleName}",
                successResource is Resource.Success
            )
            Assert.assertEquals(mockUser, (successResource as Resource.Success).data)


            coVerify { appSettingsDataStoreRepository.setUserLoggedIn(true) }
            coVerify { appSettingsDataStoreRepository.saveJwtToken(response.token) }
            coVerify { appSettingsDataStoreRepository.setUser(response.user.toUser()) }

            awaitComplete()
        }
    }

    @Test
    fun `test register api call with return error`() = runTest {
        val errorMessage = "Network Error: Please check your internet connection"
        coEvery { apiService.register(mockUser.toUserDto()) } throws IOException(errorMessage)
        userApiRepositoryImpl.register(mockUser).test {
            val loadingState = awaitItem()
            Assert.assertTrue(
                "Expected Resource.Loading but was ${loadingState::class.simpleName}",
                loadingState is Resource.Loading
            )
            val errorResource = awaitItem()
            Assert.assertTrue(
                "Expected Resource.Error but was ${errorResource::class.simpleName}",
                errorResource is Resource.Error
            )
            Assert.assertEquals(errorMessage, (errorResource as Resource.Error).message)

            awaitComplete()
        }
    }

    @Test
    fun `test login api call with return success`() = runTest {
        coEvery {
            apiService.login(
                mockUser.toUserDto().copy(fullName = null)
            )
        } returns Response.success(response)
        userApiRepositoryImpl.login(mockUser).test {
            val loadingState = awaitItem()
            Assert.assertTrue(
                "Expected Resource.Loading but was ${loadingState::class.simpleName}",
                loadingState is Resource.Loading
            )
            val successResource = awaitItem()
            Assert.assertTrue(
                "Expected Resource.Success but was ${successResource::class.simpleName}",
                successResource is Resource.Success
            )
            Assert.assertEquals(mockUser, (successResource as Resource.Success).data)
            awaitComplete()
        }
    }

    @Test
    fun `test login api call with return error`() = runTest {
        val errorMessage = "HTTP Error: HTTP 400 Response.error()"
        coEvery {
            apiService.login(
                mockUser.toUserDto().copy(fullName = null)
            )
        } throws HttpException(
            Response.error<AuthResponseDto>(
                400,
                errorMessage.toResponseBody(null)
            )
        )
        userApiRepositoryImpl.login(mockUser).test {
            val loadingState = awaitItem()
            Assert.assertTrue(
                "Expected Resource.Loading but was ${loadingState::class.simpleName}",
                loadingState is Resource.Loading
            )
            val errorResource = awaitItem()
            Assert.assertTrue(
                "Expected Resource.Error but was ${errorResource::class.simpleName}",
                errorResource is Resource.Error
            )
            Assert.assertEquals(errorMessage, (errorResource as Resource.Error).message)
            awaitComplete()
        }
    }

    @Test
    fun `test getCurrentUser api call with both return success and error`() = runTest {
        coEvery { apiService.getCurrentUser() } returns Response.success(response.user)
        userApiRepositoryImpl.getCurrentUser().test {
            val loadingState = awaitItem()
            Assert.assertTrue(loadingState is Resource.Loading)
            val successResource = awaitItem()
            Assert.assertTrue(successResource is Resource.Success)
            Assert.assertEquals(response.user.toUser(), (successResource as Resource.Success).data)
            awaitComplete()
        }
        val errorMessage = "Network Error: Please check your internet connection"
        coEvery { apiService.getCurrentUser() } throws IOException(errorMessage)
        userApiRepositoryImpl.getCurrentUser().test {
            val loadingState = awaitItem()
            Assert.assertTrue(loadingState is Resource.Loading)
            val errorResource = awaitItem()
            Assert.assertTrue(errorResource is Resource.Error)
            Assert.assertEquals(errorMessage, (errorResource as Resource.Error).message)
            awaitComplete()
        }
    }

    @Test
    fun `test updateUserProfile api call with both return success and error`() = runTest {
        coEvery { apiService.updateUserProfile(mockUser.toUserDto()) } returns Response.success(response.user)
        userApiRepositoryImpl.updateUserProfile(mockUser).test {
            val loadingState = awaitItem()
            Assert.assertTrue(loadingState is Resource.Loading)
            val successResource = awaitItem()
            Assert.assertTrue(successResource is Resource.Success)
            Assert.assertEquals(response.user.toUser(), (successResource as Resource.Success).data)
            awaitComplete()
        }
        val errorMessage = "Network Error: Please check your internet connection"
        coEvery { apiService.updateUserProfile(mockUser.toUserDto()) } throws IOException(errorMessage)
        userApiRepositoryImpl.updateUserProfile(mockUser).test {
            val loadingState = awaitItem()
            Assert.assertTrue(loadingState is Resource.Loading)
            val errorResource = awaitItem()
            Assert.assertTrue(errorResource is Resource.Error)
            Assert.assertEquals(errorMessage, (errorResource as Resource.Error).message)
            awaitComplete()
        }
    }

    @Test
    fun `test uploadProfilePicture api call with both return success and error`() = runTest {
        val profilePicture = "dummy_image_data".toByteArray()
        coEvery { apiService.uploadProfilePicture(any()) } returns Response.success(response.user)
        userApiRepositoryImpl.uploadProfilePicture(profilePicture).test {
            val loadingState = awaitItem()
            Assert.assertTrue(loadingState is Resource.Loading)
            val successResource = awaitItem()
            Assert.assertTrue(successResource is Resource.Success)
            awaitComplete()
        }
        val errorMessage = "Network Error: Please check your internet connection"
        coEvery { apiService.uploadProfilePicture(any()) } throws IOException(errorMessage)
        userApiRepositoryImpl.uploadProfilePicture(profilePicture).test {
            val loadingState = awaitItem()
            Assert.assertTrue(loadingState is Resource.Loading)
            val errorResource = awaitItem()
            Assert.assertTrue(errorResource is Resource.Error)
            awaitComplete()
        }
    }

    @Test
    fun `test deleteProfilePicture api call with both return success and error`() = runTest {
        val filename = "dummy_filename"
        coEvery { apiService.deleteProfilePicture(filename) } returns Response.success(response.user)
        userApiRepositoryImpl.deleteProfilePicture(filename).test {
            val loadingState= awaitItem()
            Assert.assertTrue(loadingState is Resource.Loading)
            val successResource = awaitItem()
            Assert.assertTrue(successResource is Resource.Success)
            awaitComplete()
        }
        val errorMessage = "Network Error: Please check your internet connection"
        coEvery { apiService.deleteProfilePicture(filename) } throws IOException(errorMessage)
        userApiRepositoryImpl.deleteProfilePicture(filename).test {
            val loadingState = awaitItem()
            Assert.assertTrue(loadingState is Resource.Loading)
            val errorResource = awaitItem()
            Assert.assertTrue(errorResource is Resource.Error)
            awaitComplete()
        }
    }
}

private val mockUser = User(
    email = "Test@gmail.com",
    fullName = "Andrew Simmons",
    password = "password123"
)
private val response = AuthResponseDto(
    user = UserDto(
        id = "eb97295e-5a66-43bc-a0e9-dac3a24c1119",
        email = "Test@gmail.com",
        fullName = "Andrew Simmons",
    ),
    token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
)
private val errorResponse = ErrorResponse(
    error = "error",
)