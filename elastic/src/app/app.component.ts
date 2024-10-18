import { Component, inject, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ProductCardComponent } from "./common-ui/product-card/product-card.component";
import { ProductService } from './services/product.service';
import { Product } from './data/interfaces/product';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ProductCardComponent, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
    
  title = 'elastic';

  productService: ProductService = inject(ProductService)
    public products: Product[] = [];

    text!: string
    color: string = "none"


  ngOnInit(): void {
    this.getAllProducts()
}

public getAllProducts(): void{
  this.productService.getAllProducts().subscribe(
      (response: Product[])=>{
          this.products=response
          console.log(this.products)
      },
      (error: HttpErrorResponse)=>{
          alert(`Проблема с сервером\n ${error.message}`)
      }
  )
}

public search(): void{
    console.log(this.color)
    if(this.text.length!=0){
    this.productService.search(this.text, this.color).subscribe(
        (response: Product[])=>{
            this.products=response
            console.log(this.products)
        },
        (error: HttpErrorResponse)=>{
            alert(`Проблема с сервером\n ${error.message}`)
        }
    )
  }else{
    this.getAllProducts()
  }
}

}
