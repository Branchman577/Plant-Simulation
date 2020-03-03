var slider;
var output;
var state_list=[];
var listOfColours=[];
var x_glob;
var y_glob;
var executed=true;
var noPlant_glob;
main();
function generate_board(data){
	var state = data;
	console.log("generate board started");
	const canvas = document.querySelector('#c');
	console.log("selected canvas");
	canvas.width=window.innerWidth;
	canvas.height=window.innerHeight;
	const renderer = new THREE.WebGLRenderer({canvas});
	console.log("generated canvas");
	var max_x=parseInt(window.y_glob)+1;
	var max_y=parseInt(window.x_glob)+1;
	window.camera = new THREE.PerspectiveCamera(90, window.innerWidth/window.innerHeight, 1, 1000); 
	console.log("generated camera");
	camera.position.z=Math.sqrt(Math.pow((Math.max(max_x,max_y+2)/(2*Math.cos(45))),2)-((Math.pow(Math.max(max_x,max_y+2),2)/2)));
	camera.position.x=(max_x-1)/2;
	camera.position.y=((max_y+2)/2);
	const scene = new THREE.Scene();
	console.log("generated scene");
	{
		const color= 0xFFFFFF;
		const intensity = 1;
		const light = new THREE.DirectionalLight(color,intensity);
		light.position.set(max_x/2,(max_y+2)/2,1500);
		scene.add(light);
	}
	const background_soil = new THREE.BoxGeometry(max_x,max_y+2,1,max_x,max_y+2);
	const background_sky = new THREE.BoxGeometry(max_x,2,1);
	const root_geo_ver = new THREE.BoxGeometry(0.2,1,0.000000000000001);
	const root_geo_hor = new THREE.BoxGeometry(1,0.2,0.000000000000001);
	const half_root_geo_ver = new THREE.BoxGeometry(0.2,0.5,0.000000000000001);
	const half_root_geo_hor = new THREE.BoxGeometry(0.5,0.2,0.000000000000001);
	const circle_geo = new THREE.CircleGeometry(0.5,64);
	const rock_geo= new THREE.PlaneGeometry(1,1,1);
	console.log("generated BoxGeometry");
	function displayCube(geometry, color, x,y,z){
		const mat = new THREE.MeshPhongMaterial({color})
		const cube =new THREE.Mesh(geometry, mat);
		console.log("generated Cube",x,y);
		scene.add(cube);
		cube.position.x=x;
		cube.position.y=y;
		cube.position.z=z;	
	}
	function displayCircle(circle_geo, color, x,y){
		const circle= new THREE.Mesh(circle_geo, new THREE.MeshBasicMaterial({color}));		
		scene.add(circle);
		console.log("generated Circle", x,y);
		circle.position.x=x;
		circle.position.y=y;
		circle.position.z=1;
	}
	function displayRoot(root_geo, color,x,y){
		const root = new THREE.Mesh(root_geo, new THREE.MeshPhongMaterial({color}));
		console.log("generated root",x,y);
		scene.add(root);
		root.position.x=x;
		root.position.y=y;
		root.position.z=1;
	}
	function displaySquare(square_geo,color,x,y,z){
		const square = new THREE.Mesh(square_geo,new THREE.MeshPhongMaterial({color})); 
		scene.add(square);
		square.position.x=x
		square.position.y=y
		square.position.z=z

	}
	displayCube(background_soil,0x301a05,(max_x/2),(max_y+2)/2,0,-1);
	displayCube(background_sky,0x445555,max_x/2,max_y+1,0);
	var x_pos=0;
	var y_pos=data.length-1;
	var y_counter=0;
	for (layer of data){
		x_pos=0;
		for(object of layer.split('||')){
			if(object[0]=='P'){
				displayCircle(circle_geo,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos,y_pos);
			}
			else if (object[0]=='R'){
				if	(checkIfRoot(data, x_pos,y_counter+1,object)&&checkIfRoot(data, x_pos+1,y_counter,object)&&checkIfRoot(data, x_pos-1,y_counter,object)&&checkIfRoot(data, x_pos,y_counter-1,object)){
					displayRoot(half_root_geo_ver,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos,y_pos+0.25);
					displayRoot(half_root_geo_ver,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos,y_pos-0.25);
					displayRoot(half_root_geo_hor,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos+0.25,y_pos);
					displayRoot(half_root_geo_hor,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos-0.25,y_pos);
				}
				else if	(checkIfRoot(data, x_pos,y_counter+1,object)&&checkIfRoot(data, x_pos+1,y_counter,object)&&checkIfRoot(data, x_pos-1,y_counter,object)){//checks below,right,left
					displayRoot(half_root_geo_ver,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos,y_pos-0.25);
					displayRoot(half_root_geo_hor,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos+0.25,y_pos);
					displayRoot(half_root_geo_hor,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos-0.25,y_pos);
				}
				else if	(checkIfRoot(data, x_pos,y_counter+1,object)&&checkIfRoot(data, x_pos+1,y_counter,object)&&checkIfRoot(data, x_pos,y_counter-1,object)){//checks below,right,above
					displayRoot(half_root_geo_ver,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos,y_pos-0.25);
					displayRoot(half_root_geo_ver,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos,y_pos+0.25);
					displayRoot(half_root_geo_hor,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos+0.25,y_pos);
				}
				else if	(checkIfRoot(data, x_pos,y_counter+1,object)&&checkIfRoot(data, x_pos-1,y_counter,object)&&checkIfRoot(data, x_pos,y_counter-1,object)){//checks below,left,above
					displayRoot(half_root_geo_ver,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos,y_pos-0.25);
					displayRoot(half_root_geo_ver,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos,y_pos+0.25);
					displayRoot(half_root_geo_hor,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos-0.25,y_pos);
				}
				else if	(checkIfRoot(data, x_pos,y_counter-1,object)&&checkIfRoot(data, x_pos+1,y_counter,object)&&checkIfRoot(data, x_pos-1,y_counter,object)){//checks above,right,left
					displayRoot(half_root_geo_ver,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos,y_pos+0.25);
					displayRoot(half_root_geo_hor,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos+0.25,y_pos);
					displayRoot(half_root_geo_hor,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos-0.25,y_pos);
				}
				else if	(checkIfRoot(data, x_pos,y_counter+1,object)&&checkIfRoot(data, x_pos+1,y_counter,object)){//checks below and right
					displayRoot(root_geo_ver,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos,y_pos-0.4);
					displayRoot(half_root_geo_hor,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos+0.3,y_pos);
				}
				else if	(checkIfRoot(data, x_pos,y_counter-1,object)&&checkIfRoot(data, x_pos+1,y_counter,object)){//checks above,right
					displayRoot(root_geo_ver,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos,y_pos+0.4);
					displayRoot(half_root_geo_hor,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos+0.3,y_pos);
				}
				else if	(checkIfRoot(data, x_pos,y_counter+1,object)&&checkIfRoot(data, x_pos-1,y_counter,object)){//checks below,left
					displayRoot(root_geo_ver,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos,y_pos-0.4);
					displayRoot(half_root_geo_hor,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos-0.3,y_pos);
				}
				else if	(checkIfRoot(data, x_pos,y_counter-1,object)&&checkIfRoot(data, x_pos-1,y_counter,object)){//checks above,left
					displayRoot(root_geo_ver,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos,y_pos+0.4);
					displayRoot(half_root_geo_hor,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos-0.3,y_pos);
				}

				else if(checkIfRoot(data, x_pos,y_counter+1,object)||(checkIfRoot(data, x_pos,y_counter-1,object))){//checks below, above
					displayRoot(root_geo_ver,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos,y_pos);
				}
				else if((checkIfRoot(data, x_pos+1,y_counter,object))||(checkIfRoot(data, x_pos-1,y_counter,object))){//checks right,left
					displayRoot(root_geo_hor,parseInt(window.listOfColours[parseInt(object[1]-1)]),x_pos,y_pos);
				}
			}
			else if (object[0]=='W'){
				displayCircle(circle_geo,0x0af9f6,x_pos,y_pos);
			}
			else if (object[0]=='N'){
				displayCircle(circle_geo,0xff6ef3,x_pos,y_pos);
			}
			else if (object[0]=='I'){
				displayCircle(circle_geo,0xffcf00,x_pos,y_pos);
			}
			else if (object[0]=='S'){
//				displayCircle(circle_geo,0x5f5750,x_pos,y_pos);
				displaySquare(rock_geo,0x5f5750,x_pos,y_pos,1);
			}

			x_pos=x_pos+1;
		}
		y_pos=y_pos-1;	
		y_counter++;
	}
	renderer.render(scene,camera);
}
function colourGenerator(){
	var red=Math.floor(Math.random()*255).toString(16);
	var green=Math.floor(Math.random()*255).toString(16);
	var blue=Math.floor(Math.random()*255).toString(16);
	return "0x"+red+green+blue;
}
function checkIfRoot(data, x,y,object){
	if(y<0){return false;}
 	else if(data[y].split("||")[x]=="P"+object.slice(1)){return true;}
 	else if(data[y].split("||")[x]=="R"+object.slice(1)){return true;}
 	else if(data[y].split("||")[x]=="Water"){return true;}
 	else if(data[y].split("||")[x]=="Nitro"){return true;}
 	else if(data[y].split("||")[x]=="Iron"){return true;}
	return false;
}
function main(){
//	console.log("got here');
	if (window.executed==true){
		window.executed=false;
		fetch('Results_trace.html')
			.then(window.executed =false)
			.then(response => response.text())
			.then(text => text.split('\n'))
			.then(function(text){
				for (x of text){
//					console.log(x);
					if (x.split("<TD>").length>1&&x.split("<TD>")[x.split("<TD>").length-1].split(" ")[1]=='Board'&&x.split("<TD>")[x.split("<TD>").length-1].split(" ")[2]=='size:'){
						window.x_glob=x.split("<TD>")[x.split("<TD>").length-1].split(" ")[3];
						window.y_glob=x.split("<TD>")[x.split("<TD>").length-1].split(" ")[4];
						window.noPlant_glob=x.split("<TD>")[x.split("<TD>").length-1].split(" ")[5][0];
					}	
					else if (x.split("<TD>")[x.split("<TD>").length-1].split("##").length>1){
						let temp = x.split("<TD>")[x.split("<TD>").length-1].split("##");//.split('||');
						window.state_list.push(temp);
					}
				}
			})
			.then(function(){
				var i=0;
				while(i<150){
//				while(i<parseInt(window.noPlant_glob)){
					window.listOfColours.push(colourGenerator());
					i=i+1;
				}
			})
			.then(function(){

				var slider_config='<input type="range" min="1" max="1500" value="1" class="slider" id="myRange">';
				document.getElementById('slideContainer').innerHTML = slider_config
				window.slider = document.getElementById('myRange');
				window.output = document.getElementById('displayValue');
				window.output.innerHTML=0;
				generate_board(window.state_list[0]);
				window.slider.oninput = function(){
					window.output.innerHTML= (this.value-1);
					generate_board(window.state_list[this.value-1]);
				}

			})	
	}

}
