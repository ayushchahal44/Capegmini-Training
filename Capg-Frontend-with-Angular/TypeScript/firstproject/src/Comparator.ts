interface Comparator<T> {
  compare(ob1: T, ob2: T): number;
}

function sort<T>(a: T[], com: Comparator<T>): T[] {
  for (let i = 0; i < a.length - 1; i++) {
    for (let j = 0; j < a.length - 1 - i; j++) {
      if (com.compare(a[j], a[j + 1]) > 0) {
        let temp = a[j];
        a[j] = a[j + 1];
        a[j + 1] = temp;
      }
    }
  }
  return a;
}

interface User {
  id: number;
  name: string;
}

let ar: User[] = [
  { id: 2, name: 'Bob' },
  { id: 1, name: 'Alice' },
  { id: 3, name: 'Charlie' }
];

let comp: Comparator<User> = {
  compare: (ob1, ob2) => ob1.id - ob2.id
};

sort(ar, comp);
console.log(ar);