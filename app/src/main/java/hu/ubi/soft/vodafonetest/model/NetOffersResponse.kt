package hu.ubi.soft.vodafonetest.model


import com.google.gson.annotations.SerializedName

data class NetOffersResponse(
    @SerializedName("postpaid")
    val postpaid: Postpaid?,
    @SerializedName("responseType")
    val responseType: String?,
    @SerializedName("status")
    val status: String?
) {
    data class Postpaid(
        @SerializedName("cumulativeActualDataUsage")
        val cumulativeActualDataUsage: Int?,
        @SerializedName("cumulativeActualSharedDataUsage")
        val cumulativeActualSharedDataUsage: Int?,
        @SerializedName("cumulativeTotalData")
        val cumulativeTotalData: Int?,
        @SerializedName("cumulativeTotalSharedData")
        val cumulativeTotalSharedData: Int?,
        @SerializedName("entitlements")
        val entitlements: List<String?>?,
        @SerializedName("noRedir")
        val noRedir: Boolean?,
        @SerializedName("offers")
        val offers: List<Any?>?,
        @SerializedName("poolEntitlements")
        val poolEntitlements: List<String?>?,
        @SerializedName("poolId")
        val poolId: String?,
        @SerializedName("poolRole")
        val poolRole: String?,
        @SerializedName("pools")
        val pools: List<Pool?>?,
        @SerializedName("refills")
        val refills: List<Refill?>?,
        @SerializedName("unlimitedContentPackageMessage")
        val unlimitedContentPackageMessage: String?,
        @SerializedName("unlimitedContentPackages")
        val unlimitedContentPackages: List<UnlimitedContentPackage?>?,
        @SerializedName("warningText")
        val warningText: String?
    ) {
        data class Pool(
            @SerializedName("active")
            val active: Boolean?,
            @SerializedName("activeOnSubscription")
            val activeOnSubscription: Boolean?,
            @SerializedName("actualUsage")
            val actualUsage: Int?,
            @SerializedName("addonIdsMaster")
            val addonIdsMaster: List<Any?>?,
            @SerializedName("addonIdsMember")
            val addonIdsMember: List<Any?>?,
            @SerializedName("baseOfferIncluded")
            val baseOfferIncluded: Boolean?,
            @SerializedName("dataUsageMb")
            val dataUsageMb: Int?,
            @SerializedName("description")
            val description: String?,
            @SerializedName("expirationDate")
            val expirationDate: String?,
            @SerializedName("featured")
            val featured: Boolean?,
            @SerializedName("id")
            val id: String?,
            @SerializedName("maxMember")
            val maxMember: Int?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("offerIdsMaster")
            val offerIdsMaster: List<Any?>?,
            @SerializedName("offerIdsMember")
            val offerIdsMember: List<Any?>?,
            @SerializedName("overfill")
            val overfill: Int?,
            @SerializedName("price")
            val price: Int?,
            @SerializedName("psmCodes")
            val psmCodes: PsmCodes?,
            @SerializedName("refillIds")
            val refillIds: List<String?>?,
            @SerializedName("total")
            val total: Int?,
            @SerializedName("type")
            val type: String?,
            @SerializedName("unlimitedContentPackages")
            val unlimitedContentPackages: List<String?>?,
            @SerializedName("upgradePoolIds")
            val upgradePoolIds: List<Any?>?
        ) {
            data class PsmCodes(
                @SerializedName("activation")
                val activation: String?
            )
        }

        data class Refill(
            @SerializedName("active")
            val active: Boolean?,
            @SerializedName("description")
            val description: String?,
            @SerializedName("id")
            val id: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("price")
            val price: Int?,
            @SerializedName("psmCodes")
            val psmCodes: PsmCodes?
        ) {
            data class PsmCodes(
                @SerializedName("activation")
                val activation: String?,
                @SerializedName("deactivation")
                val deactivation: String?
            )
        }

        data class UnlimitedContentPackage(
            @SerializedName("active")
            val active: Boolean?,
            @SerializedName("activeOnSubscription")
            val activeOnSubscription: Boolean?,
            @SerializedName("description")
            val description: String?,
            @SerializedName("expirationDate")
            val expirationDate: String?,
            @SerializedName("featured")
            val featured: Boolean?,
            @SerializedName("id")
            val id: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("price")
            val price: Int?,
            @SerializedName("psmCodes")
            val psmCodes: PsmCodes?,
            @SerializedName("subscriptionType")
            val subscriptionType: String?
        ) {
            data class PsmCodes(
                @SerializedName("activation")
                val activation: String?,
                @SerializedName("deactivation")
                val deactivation: String?
            )
        }
    }
}