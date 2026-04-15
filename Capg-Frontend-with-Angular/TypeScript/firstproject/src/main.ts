import './style.css'
import { setupCounter } from './counter.ts'
import { populateLaptops } from './populateLaptops.ts'
import { populateUsers } from './populateUsers.ts'

document.querySelector<HTMLDivElement>('#app')!.innerHTML = `
<section id="center">
  <div>
    <h1>Get started</h1>
  </div>
  <button id="counter" type="button" class="counter"></button>
  <table id="laptops">
    <thead>
      <tr>
        <th>Brand</th>
        <th>Model</th>
        <th>Price</th>
      </tr>
    </thead>
    <tbody>
    </tbody>
  </table>

  <table id="users">
    <thead>
      <tr>
        <th>Name</th>
        <th>Email</th>
      </tr>
    </thead>
    <tbody>
    </tbody>
  </table>

  </Section>
`

setupCounter(document.querySelector<HTMLButtonElement>('#counter')!)
populateLaptops(document.querySelector<HTMLTableElement>('#laptops')!, 'http://localhost:3000/laptops');
populateUsers(document.querySelector<HTMLTableElement>('#users')!, 'http://localhost:3000/users');
