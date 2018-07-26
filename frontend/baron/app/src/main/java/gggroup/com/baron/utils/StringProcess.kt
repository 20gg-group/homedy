package gggroup.com.baron.utils

class StringProcess {
    companion object {

        fun getDistrictCity(address: String) : String {
            val temp = address.split("Quận")
            if (temp.size == 2)
                return "Quận" + temp[1]
            return address
        }

        fun getDistrict(address: String) : String {
            val districtCity = getDistrictCity(address)
            val temp = districtCity.split(",")
            if (temp.size == 2)
                return temp[0]
            return districtCity
        }

        fun getStreet(address: String) : String {
            val temp = address.split(",")
            if (temp.isNotEmpty())
                return temp[0]
            return address
        }
    }
}