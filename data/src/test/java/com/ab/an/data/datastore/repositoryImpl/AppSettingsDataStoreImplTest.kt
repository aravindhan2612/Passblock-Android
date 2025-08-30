package com.ab.an.data.datastore.repositoryImpl

import androidx.datastore.core.DataStore
import app.cash.turbine.test
import com.ab.an.data.database.PassblockDatabase
import com.ab.an.data.datastore.serializer.AppSettingsEntity
import com.ab.an.data.impl.AppSettingsDataStoreImpl
import com.ab.an.data.mapper.toAppSettings
import com.ab.an.data.mapper.toUser
import com.ab.an.data.network.dto.UserDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.slot
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppSettingsDataStoreImplTest {

    @get:Rule
    val mockRule = MockKRule(this)


    private lateinit var appSettingsDataStoreImpl: AppSettingsDataStoreImpl
    @RelaxedMockK
    private lateinit var appDataStore: DataStore<AppSettingsEntity>

    @MockK
    private lateinit var passblockDatabase: PassblockDatabase
    private lateinit var mockAppSettingsEntity: AppSettingsEntity

    @Before
    fun setup() {
        appSettingsDataStoreImpl = AppSettingsDataStoreImpl(appDataStore, passblockDatabase)
        mockAppSettingsEntity = AppSettingsEntity(
            isOnBoardingShown = true,
            user = UserDto(
                id = "eb97295e-5a66-43bc-a0e9-dac3a24c1119",
                email = "Test@gmail.com",
                fullName = "Andrew Simmons",
                password = "password123",
                phoneNumber = "2313545666",
                profilePicture = "https://example.com/profile.jpg",
                dob = "1995-08-04T05:30:00.000Z"
            ),
            isUserLoggedIn = true,
            jwtKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJlYjk3Mjk1ZS01YTY2LTQzYmMtYTBlOS1kYWMzYTI0YzExMTkiLCJlbWFpbCI6IlRlc3RAZ21haWwuY29tIiwiaWF0IjoxNzU1NjczMTIwLCJleHAiOjE3NTU2ODc1MjB9.oUZBeqIyVZXvzsKqhsPy7KrUkjgxxJZxLe3IIillGaI"
        )
    }

    // using turbine library
    @Test
    fun `getOnBoardShown return data from datastore`() = runTest {

        every { appDataStore.data } returns flowOf(mockAppSettingsEntity)
        appSettingsDataStoreImpl.getOnBoardShown().test {
            val actualValue = awaitItem()
            Assert.assertEquals(mockAppSettingsEntity.isOnBoardingShown, actualValue)
            awaitComplete()
        }
    }

    @Test
    fun `setOnBoardShown update data in datastore`() = runTest {
        val updateLambdaSlot = slot<suspend (AppSettingsEntity) -> AppSettingsEntity>()
        coEvery { appDataStore.updateData(capture(updateLambdaSlot)) } coAnswers {
            updateLambdaSlot.captured.invoke(mockAppSettingsEntity)
        }
        appSettingsDataStoreImpl.setOnBoardShown(false)
        coVerify { appDataStore.updateData(any()) }
    }

    @Test
    fun `getUser return data from datastore`() = runTest {

        every { appDataStore.data } returns flowOf(mockAppSettingsEntity)

        val resultFlow = appSettingsDataStoreImpl.getUser()
        val actualValue = resultFlow.first()// Collect the first emitted value

        Assert.assertEquals(mockAppSettingsEntity.user.toUser(), actualValue)
    }

    @Test
    fun `setUser update data in datastore`() = runTest {
        val userToSetInput = mockAppSettingsEntity.user.copy(
            fullName = "Updated Name"
        ).toUser()

        val updateLambdaSlot = slot<suspend (AppSettingsEntity) -> AppSettingsEntity>()
        coEvery { appDataStore.updateData(capture(updateLambdaSlot)) } coAnswers {
            updateLambdaSlot.captured.invoke(mockAppSettingsEntity)
        }

        appSettingsDataStoreImpl.setUser(userToSetInput)

        coVerify { appDataStore.updateData(any()) }
        val capturedLambda = updateLambdaSlot.captured
        val transformedEntity = capturedLambda.invoke(mockAppSettingsEntity)
        Assert.assertEquals("Updated Name", transformedEntity.user.fullName)
    }

    @Test
    fun `getAppSettings from datastore`() = runTest {
        every { appDataStore.data } returns flowOf(mockAppSettingsEntity)
        appSettingsDataStoreImpl.getAppSettings().test {
            val actualValue = awaitItem()
            Assert.assertEquals(mockAppSettingsEntity.toAppSettings(), actualValue)
            awaitComplete()

        }
    }

    @Test
    fun `setUserLoggedIn update data in datastore`() = runTest {
        val updateLambdaSlot = slot<suspend (AppSettingsEntity) -> AppSettingsEntity>()
        coEvery { appDataStore.updateData(capture(updateLambdaSlot)) } coAnswers {
            updateLambdaSlot.captured.invoke(mockAppSettingsEntity)
        }
        appSettingsDataStoreImpl.setUserLoggedIn(false)
        coVerify { appDataStore.updateData(any()) }
    }

    @Test
    fun `isUserLoggedIn return data from datastore`() = runTest {
        every { appDataStore.data } returns flowOf(mockAppSettingsEntity)
        appSettingsDataStoreImpl.isUserLoggedIn().test {
            val actualValue = awaitItem()
            Assert.assertEquals(mockAppSettingsEntity.isUserLoggedIn, actualValue)
            awaitComplete()
        }
    }

    @Test
    fun `saveJwtToken update data in datastore`() = runTest {
        val updateLambdaSlot = slot<suspend (AppSettingsEntity) -> AppSettingsEntity>()
        coEvery { appDataStore.updateData(capture(updateLambdaSlot)) } coAnswers {
            updateLambdaSlot.captured.invoke(mockAppSettingsEntity)
        }
        appSettingsDataStoreImpl.saveJwtToken("newToken")
        coVerify { appDataStore.updateData(any()) }
    }

    @Test
    fun `getJwtToken return data from datastore`() = runTest {
        every { appDataStore.data } returns flowOf(mockAppSettingsEntity)
        appSettingsDataStoreImpl.getJwtToken().test {
            val actualValue = awaitItem()
            Assert.assertEquals(mockAppSettingsEntity.jwtKey, actualValue)
            awaitComplete()
        }
    }
}