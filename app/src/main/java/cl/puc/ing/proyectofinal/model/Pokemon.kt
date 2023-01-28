package cl.puc.ing.proyectofinal.model

import java.io.Serializable

data class Pokemon(

    var id: Int,
    var name: String,
    var height: Double,
    var weight: Double,
    var sprite: Sprite
) : Serializable

data class Sprite(
    var frontDefault: String,
    var frontShiny: String
): Serializable


