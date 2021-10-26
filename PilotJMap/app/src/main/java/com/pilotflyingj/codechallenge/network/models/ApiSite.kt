@file:JvmName("Response")
@file:JvmMultifileClass // Add Extensions this way in order to reduce the number  of generated files.
package com.pilotflyingj.codechallenge.network.models

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable
/**
 * A Serializable Object to hold the response returned from the RESTFUL api.
 *  I could remove some data but we might want to incorporate that date into application
 *  at a later date.
 */
@Serializable
data class ApiSite(

	@Json(name="StoreName")
	val storeName: String,

	@Json(name="Address")
	val address: String,

	@Json(name="ZipCode")
	val zipCode: String? = null,

	@Json(name="StoreNo")
	val storeNo: Int? = null,

	@Json(name="Latitude")
	val latitude: Double,

	@Json(name="City")
	val city: String? = null,

	@Json(name="Longitude")
	val longitude: Double,

	@Json(name="StoreFrontBrand")
	val storeFrontBrand: String? = null,

	@Json(name="StoreType")
	val storeType: String? = null,

	@Json(name="Area")
	val area: String? = null,

	@Json(name="State")
	val state: String? = null,

	@Json(name="Phone")
	val phone: String? = null,

	@Json(name="AddressTwo")
	val addressTwo: String? = null,

	@Json(name="SpaceAvailability")
	val spaceAvailability: List<SpaceAvailabilityItem?>? = null,

	@Json(name="Country")
	val country: String? = null,

	@Json(name="Interstate")
	val interstate: String? = null
)

@Serializable
data class SpaceAvailabilityItem(

	@Json(name="Description")
	val description: String? = null,

	@Json(name="Price")
	val price: Int? = null,

	@Json(name="Total")
	val total: Int? = null,

	@Json(name="LocationID")
	val locationID: Int? = null,

	@Json(name="Booked")
	val booked: Int? = null,

	@Json(name="Available")
	val available: Int? = null,

	@Json(name="ItemID")
	val itemID: Int? = null
)