package cinema

fun representCinema(rows: UInt, seatsPerRow: UInt) : MutableList<MutableList<String>> {
    val cinema2D : MutableList<MutableList<String>> = mutableListOf()
    for (n in 0..rows.toInt()) {
        val array: MutableList<String>
        if (n == 0) {
            array = mutableListOf(" ")
        } else {
            array = mutableListOf(n.toString())
            repeat(seatsPerRow.toInt()) {
                array.add("S")
            }
        }
        cinema2D.add(array)
    }
    for (n in 1..seatsPerRow.toInt()) cinema2D[0].add(n.toString())
    return cinema2D
}

fun showSeats(cinema: MutableList<MutableList<String>>) : Unit {
    println()
    println("Cinema:")
    for (arr in cinema) println(arr.joinToString(" "))
    println()
}

fun getTicketPrice(rows: UInt, seatsPerRow: UInt, row: UInt) : UInt {
    val seats = rows * seatsPerRow
    val price = if (seats > 60u) {
        if (row > (rows / 2u)) 8u else 10u
    } else {
        10u
    }
    println("Ticket price: \$${price}")
    println()
    return price
}

fun buyTicket(rows: UInt, seatsPerRow: UInt, cinema: MutableList<MutableList<String>>) : UInt {
    var selectedRow : UInt = 0u
    var buying = true

    while(buying) {
        var selectedSeat = 0u
        try {
            println("Enter a row number:")
            selectedRow = readln().toUInt()
            println("Enter a seat number in that row:")
            selectedSeat = readln().toUInt()
            println()
        } catch (e: NumberFormatException) {
            println("Wrong input!")
            println()
        }
        try {
            if (cinema[selectedRow.toInt()][selectedSeat.toInt()] == "B") {
                println("That ticket has already been purchased!")
                println()
            } else {
                cinema[selectedRow.toInt()][selectedSeat.toInt()] = "B"
                buying = false
            }
        } catch (e: IndexOutOfBoundsException) {
            println("Wrong input!")
            println()
        }
    }
    return getTicketPrice(rows, seatsPerRow, selectedRow)
}

fun stats(seatsPerRow: UInt, rows: UInt, data: MutableList<MutableList<UInt>>, cinema: MutableList<MutableList<String>>) : Unit {
    val ticketSold = data.last().first()
    val income = data.last().last()

    val seats = rows * seatsPerRow
    val maxIncome : UInt = if (seats > 60u) {
       ((rows / 2u) * (seatsPerRow * 10u)) + (((rows / 2u) + 1u) * (seatsPerRow *8u))
    } else {
        seats * 10u
    }

    val percentage = (ticketSold.toDouble() / seats.toDouble()) * 100.0

    println("Number of purchased tickets: ${data[1].first()}")
    println("Percentage: ${"%.2f".format(percentage)}%")
    println("Current income: \$$income")
    println("Total income: \$$maxIncome")
    println()
}

fun main() {
    var skip = false
    var rows = 0u
    var seatsPerRow = 0u

    while (!skip) {
        try {
            println("Enter the number of rows:")
            rows = readln().toUInt()
            println("Enter the number of seats in each row:")
            seatsPerRow = readln().toUInt()
            println()
            skip = true
        } catch (e: NumberFormatException) {
            println("Wrong input!")
            println()
        }
    }
    
    val cinema2D = representCinema(rows, seatsPerRow)
    val data : MutableList<MutableList<UInt>> = mutableListOf(mutableListOf(0u, 0u), mutableListOf(0u, 0u))

    while (true) {
        println()
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        when (readln().toInt()) {
            0 -> break
            1 -> showSeats(cinema2D)
            2 -> {
                val price = buyTicket(rows, seatsPerRow, cinema2D)
                data[0] = mutableListOf(1u, price)
                data[1][0]++
                data[1][1] += price
            }
            3 -> {
                stats(seatsPerRow, rows, data, cinema2D)
            }
        }
    }
}
