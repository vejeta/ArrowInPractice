package com.fortysevendeg.arrowinpractice.database

import com.fortysevendeg.arrowinpractice.model.House
import com.fortysevendeg.arrowinpractice.model.HouseId
import com.fortysevendeg.arrowinpractice.model.PostHouse
import java.util.*

class HousesDatabase {

  /**
   * Just a bunch of them, we're missing some houses for sure! ¯\_(ツ)_/¯.
   *
   * All collections are intentionally mutable since they mimic what a database would be. We need to be able to insert
   * (store) new elements at any level.
   */
  private val houses = Collections.synchronizedList(mutableListOf(
    House(1, "Stark", "They are the ruler of the north or in other words the main house of the north. They rule from the Castle of Winterfell."),
    House(2, "Lannister", "They are the warden of the west or the main house of the west. The rule from the Casterly Rock of the Westerlands."),
    House(3, "Baratheon", "Their dynasty starts after Robert Baratheon defeated The Mad King."),
    House(4, "Targaryen", "They ruled the seven kingdoms for 300 years with the help of their Dragons. But during the rule of the Mad King Aerys they lost their battle against Robert and Ned and lost their claim in the Iron Throne."),
    House(5, "Greyjoy", "They are the lords of Iron Islands rule from the Pyke in the Iron Islands."),
    House(6, "Arryn", "They are the main House of the Vale and rule form a small castle called the Eyrie in the mountain."),
    House(7, "Martell", "They are the ruler of the Dorne , rule from the Sunspear Castle ."),
    House(8, "Tully", "House Tully rule from Riverrun in the Riverlands. Ned Strak’s wife Cathlyn is the daughter of this house."),
    House(9, "Tyrell", "Targaryens made the Tyrells Lords of Highgardens . They are the main house in the Reach and rule from Highgarden.")))

  @Synchronized @Throws(RandomDBException::class)
  fun getAll(): List<House> = houses

  @Synchronized @Throws(RandomDBException::class)
  fun getByName(houseName: String): House? = houses.find { it.name.toLowerCase() == houseName.toLowerCase() }

  @Synchronized @Throws(RandomDBException::class)
  fun getById(id: Long): House? = houses.find { it.houseId == id }

  /**
   * Enabling indexed access operator for fetching by houseId, as in housesDB[8].
   */
  @Throws(RandomDBException::class)
  operator fun get(id: Long): House? = getById(id)

  /**
   * Enabling indexed access operator for fetching by name, as in housesDB["Stark"].
   */
  @Throws(RandomDBException::class)
  operator fun get(houseName: String): House? = getByName(houseName)

  /**
   * Inserts a new House in the Database or updates an existent one in case there's already one stored with the same
   * name.
   *
   * @return true if created, false if updated.
   */
  @Synchronized @Throws(RandomDBException::class)
  fun createOrUpdate(postedHouse: PostHouse): Boolean {
    val storedHouse = getByName(postedHouse.name)
    return if (storedHouse != null) {
      val position = houses.indexOf(storedHouse)
      houses[position] = House(
        storedHouse.houseId,
        postedHouse.name,
        postedHouse.description)
      false
    } else {
      houses.add(House(
        (houses.last().houseId + 1),
        postedHouse.name,
        postedHouse.description))
      true
    }
  }
}
