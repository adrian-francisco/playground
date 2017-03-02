// different ways to parse numbers, parse* will parse upto the number, Number will expect a proper number
let pi = parseInt("123hiu");
let nu = Number("123hiu");
let pf = parseFloat("1.23.2fds");
console.log(`parseInt: ${pi}, number: ${nu}, parseFloat: ${pf}`);

// string compare doesn't convert to numbers
// converting one side to number does proper number checking
let k = "10000";
let p = "360";
console.log(k >= p);
console.log(Number(k) >= p);
console.log(k >= Number(p));

// different for loops (in vs. of)
var employee = ['001', '002', '003', '004', '005', '006', '007'];
for(let i in employee){
    console.log(i);
}
console.log();
for(let i of employee){
    console.log(i);
}
 
// use let
var blah = "hello world!";
console.log(blah);
var blah = "world hello!";
console.log(blah);

// arrow functions suck
var getA = a => a + a;
console.log(getA(2));

// use triple === to check value and type
var a = "blah";
var b = new String("blah");
console.log("triple equals: " + (a===b));
console.log("double equals: " + (a==b));

// wtf is a Symbol?
var sym = Symbol("something");
console.log(sym);

// use [] for array initialization
var arr = ["a", "b", "c", "d"];

// returns Infinity
console.log(10/0);

// returns NaN
console.log(Math.sqrt(-1));

var type = "grizzly";
let name = "bear";

// constants, same old
const PI = Math.PI;

class Bear {
	constructor()
	{
		this.type = 'bear';
	}
	
	says(say) {
		console.log(this.type + " says " + say);
	}
}

let bear = new Bear();
let says = "growl";
bear.says(says);

class Grizzly extends Bear {
	constructor()
	{
		super();
		this.type = "grizzly";
	}
}

bear = new Grizzly();
bear.says("growl");

// template strings are cool!
bear = "grizzly";
says = "growl";
console.log(`${bear} says ${says}`);

// auto set names
let zoo = {bear, says};
console.log(zoo);

// auto set values
let grizzly = { t: "grizzly", many : 2, more: 5};
let {t, many} = grizzly;
console.log(`${t} and ${many}`);

// varargs, nice
function bears (...types) {
	console.log(types);
}
bears("polar", "grizzly", "koala");
