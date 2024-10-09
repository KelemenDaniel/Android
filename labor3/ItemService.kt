package labor3

class ItemService(private val itemRepository: ItemRepository) {

    fun selectRandomItems(numberOfItems: Int):List<Item>{
        if(numberOfItems> itemRepository.size()){
            println("The given number is higher than the number of existing items!")
            return emptyList()
        }
        val items = mutableListOf<Item>()

        while(items.size < numberOfItems)
        {
            val item = itemRepository.randomItem()
            if(!items.contains(item))
                items.add(item)
        }
        return items
    }
}