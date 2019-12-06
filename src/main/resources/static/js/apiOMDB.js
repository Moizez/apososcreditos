
$(document.getElementById("div")).ready(function () {
   $("#div").hide();
});

async function getJSON(url, apiKey, parm, typeSearch) {
    return await new Promise(resolve => {
        $.getJSON(url+"/?"+typeSearch+"="+parm+"&apikey="+apiKey).done(data=>{
           resolve(data);
        }).fail(error=>{
            resolve("error");
        });
    });
}

async function searchFilmeName(text) {
  const url = "http://www.omdbapi.com";
  const apiKey = "35c8b8b8";
  const api = await getJSON(url, apiKey, text, "t");
  preencheInputs(api);
  console.log(api);
}

$("#search").keyup(function () {
   searchFilmeName($(this).val());
});

function creatTag(tag, idPai) {
  const pai = document.getElementById(idPai);
  const element = document.createElement(tag);
  pai.appendChild(element);
  return element;
}

function gerarBytesImg(urlImg) {
    var reader = new window.FileReader();
    let blob = new Blob([urlImg], {type: 'image/jpg'});
    //reader.readAsBinaryString(blob);
    reader.readAsDataURL(blob);
    reader.onloadend = function () {
        $("#customFile").change(function () {
            $(this)[0].files[0].name = reader.result;
        });
    };
}

function preencheInputs(data) {
    $("#alert-notFound").html("");
    $("#poster").html("");
    if(data.Error != "Movie not found!"){
    	$("#div").hide();
        $("#title").val(data.Title);
        const img = creatTag("img", "poster");
        img.src = data.Poster;
        $("#diretor").val(data.Director);
        $("#genero").val(data.Genre);
        $("#elenco").val(data.Actors);
        $('#produtora').val(data.Production);
        $("#duracao").val(data.Runtime); 
        $("#sinopse").val(data.Plot);
        $("#pais").val(data.Country);
        $("#ano").val(data.Year);
        gerarBytesImg(img.src);
    }else{
        $("#div").show();
        $("#title").val("");
        $("#diretor").val("");
        $("#genero").val("");
        $("#elenco").val("");
        $("#produtora").val("");
        $("#duracao").val("");
        $("#sinopse").val("");
        $("#pais").val("");
        $("#ano").val("");
        $("#alert-notFound").html("<span>Filme não encontrado.</span>")
    }
}

