function processData(input) {
	let array = input.split("\n");
	
	let n = array[0];
	
	for (let i=1; i<=n; i++) {
		let word = array[i];
		let length = word.length - 1;
		let half = word.length / 2;
		let ops = 0;
		
		for (let j=0; j<half; j++) {
			let front = word.charCodeAt(j);
			let back = word.charCodeAt(length - j);
			
			if (front !== back) {
				ops += Math.abs(front - back);
			}
		}
		
		console.log(ops);
	}
} 

process.stdin.resume();
process.stdin.setEncoding("ascii");
_input = "";
process.stdin.on("data", function (input) {
    _input += input;
});

process.stdin.on("end", function () {
   processData(_input);
});