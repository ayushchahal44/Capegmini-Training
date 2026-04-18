import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LaptopObject } from './components/laptop-object/laptop-object';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, LaptopObject],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected title = 'secondNgApp';

  laptops = [
  {
    "id": 1,
    "brand": "Dell",
    "model": "XPS 13",
    "price": 90000
  },
  {
    "id": 2,
    "brand": "Apple",
    "model": "MacBook Pro",
    "price": 120000
  },
  {
    "id": 3,
    "brand": "HP",
    "model": "Spectre x360",
    "price": 87000
  },
  {
    "id": 4,
    "brand": "Lenovo",
    "model": "ThinkPad X1 Carbon",
    "price": 95000
  },
  {
    "id": 5,
    "brand": "Asus",
    "model": "ROG Zephyrus G14",
    "price": 110000
  },
  {
    "id": 6,
    "brand": "Acer",
    "model": "Predator Helios 300",
    "price": 98000
  },
  {
    "id": 7,
    "brand": "MSI",
    "model": "GF63 Thin",
    "price": 75000
  },
  {
    "id": 8,
    "brand": "Samsung",
    "model": "Galaxy Book 3",
    "price": 88000
  },
  {
    "id": 9,
    "brand": "Microsoft",
    "model": "Surface Laptop 5",
    "price": 105000
  },
  {
    "id": 10,
    "brand": "Razer",
    "model": "Blade 15",
    "price": 150000
  },
  {
    "id": 11,
    "brand": "LG",
    "model": "Gram 17",
    "price": 115000
  },
  {
    "id": 12,
    "brand": "Huawei",
    "model": "MateBook X Pro",
    "price": 99000
  },
  {
    "id": 13,
    "brand": "Dell",
    "model": "Inspiron 15",
    "price": 65000
  }
];
}
