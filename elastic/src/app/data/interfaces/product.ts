import { Sku } from "./sku"

export interface Product {
    id: number
    name: string
    price: number
    date: Date
    description: string
    skuList: Sku[]
}
