
const listResut = [
                ["Me enamoré","Shakira", "3:45", "https://www.amazon.es/Me-Enamor%C3%A9/dp/B06Y1DRZBC/ref=sr_1_1?s=dmusic&ie=UTF8&qid=1497312478&sr=1-1&keywords=shakira"],
                ["40:1","Sabatón", "1:20", "https://www.amazon.es/40-1/dp/B00950RHUM/ref=sr_1_5?s=dmusic&ie=UTF8&qid=1497312356&sr=1-5&keywords=40%3A1"],
              ];

var organizadoTitulo = 0;
var organizadoArtista = 0;
var organizadoDuracion = 0;

$( document ).ready(function() {

    $("#botonBuscar").click(function() {
      onBotonBuscar();
    });

    $("#col-titulo-encabezado").click(function() {
      onOrderTitulo();
      limpiarArrows();
      direccionArrow("titulo", organizadoTitulo);
    });

    $("#col-artista-encabezado").click(function() {
      onOrderArtista();
      limpiarArrows();
      direccionArrow("artista", organizadoArtista);
    });

    $("#col-duracion-encabezado").click(function() {
      onOrderDuracion();
      limpiarArrows();
      direccionArrow("duracion", organizadoDuracion);
    });

});

function limpiarArrows(){

  $("#col-duracion-encabezado img").attr("src","");
  $("#col-artista-encabezado img").attr("src","");
  $("#col-titulo-encabezado img").attr("src","");
}

function direccionArrow(tipo, direccion){
  if(direccion == 1){
    $("#col-"+tipo+"-encabezado img").attr("src","./img/arrow-down.png");
  }
  if(direccion == 2){
    $("#col-"+tipo+"-encabezado img").attr("src","./img/arrow-up.png");
  }
}

function onBotonBuscar(){

  /*var listaRes = listResut.slice();
  var list = "";

  list = generarElementoLista(listaRes);

  $("#listaResultados").html(list);*/

  $("#listaResultados").html(window.JSInterface.getWebAmazon($("#inputBuscador").val()));
}

function generarElementoLista(lista){

  var listAux = "";

  for(var i=0;i<lista.length; i++){

    listAux += '<div class="col-xs-12 searchResUnit">';
    listAux += '<div class="col-xs-5 col-titulo"><a onClick="abrirLinkCancion(';
    listAux += "'"+lista[i][3]+"'";
    listAux += ')">'+lista[i][0]+'</a></div>';
    listAux += '<div class="col-xs-4 col-artista">'+lista[i][1]+'</div>';
    listAux += '<div class="col-xs-3 col-duracion">'+lista[i][2]+'</div>';
    listAux += '</div>';
  }
  return listAux;
}

function abrirLinkCancion(link){
  window.JSInterface.loadAmazonWeb(link);
}

function onOrderTitulo(){
    let a = listResut.slice();
    if(this.organizadoTitulo == 0 || this.organizadoTitulo == 2){
      a.sort(
        function(a,b){
          if (a[0] < b[0])
            return -1;
          if (a[0] > b[0])
            return 1;
          return 0;
        });
        $("#listaResultados").html(generarElementoLista(a));
        this.organizadoTitulo = 1;
    } else{
        a.sort(
          function(a,b){
            if (a[0] > b[0])
              return -1;
            if (a[0] < b[0])
              return 1;
            return 0;
          });
          $("#listaResultados").html(generarElementoLista(a));
          this.organizadoTitulo = 2;
    }
  }

function onOrderArtista(){
    let a = listResut.slice();
    if(this.organizadoArtista == 0 || this.organizadoArtista == 2){
      a.sort(
        function(a,b){
          if (a[1] < b[1])
            return -1;
          if (a[1] > b[1])
            return 1;
          return 0;
        });
        $("#listaResultados").html(generarElementoLista(a));
        this.organizadoArtista = 1;
    } else{
        a.sort(
          function(a,b){
            if (a[1] > b[1])
              return -1;
            if (a[1] < b[1])
              return 1;
            return 0;
          });
          $("#listaResultados").html(generarElementoLista(a));
          this.organizadoArtista = 2;
    }
  }

function onOrderDuracion(){
    let a = listResut.slice();
    if(this.organizadoDuracion == 0 || this.organizadoDuracion == 2){
      a.sort(
        function(a,b){
          if (a[2] < b[2])
            return -1;
          if (a[2] > b[2])
            return 1;
          return 0;
        });
        $("#listaResultados").html(generarElementoLista(a));
        this.organizadoDuracion = 1;
    } else{
        a.sort(
          function(a,b){
            if (a[2] > b[2])
              return -1;
            if (a[2] < b[2])
              return 1;
            return 0;
          });
          $("#listaResultados").html(generarElementoLista(a));
          this.organizadoDuracion = 2;
    }
  }

  function checkModoNocturno(modo){
    if (modo) {
      $("#listaResultados").html("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    }else {
      $("#listaResultados").html("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
    }


  }
