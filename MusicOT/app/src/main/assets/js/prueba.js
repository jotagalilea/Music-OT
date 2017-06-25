
var listResut = [];

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

    checkModoNocturno(window.JSInterface.getGetModoNoct());

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

  if($("#inputBuscador").val() != ""){
    this.listResut = extractArrayResult().elem;

    $("#listaResultados").html(generarElementoLista(this.listResut));
  }else{
    $("#listaResultados").html("¡El campo de búsqueda está vacio!");
  }
}

function extractArrayResult(){

  return JSON.parse(window.JSInterface.getWebAmazon($("#inputBuscador").val()));
}

function generarElementoLista(lista){

  var listAux = "";

  for(var i=0;i<lista.length; i++){

    listAux += '<div class="col-xs-12 searchResUnit">';
    listAux += '<div class="col-xs-5 col-titulo"><a onClick="abrirLinkCancion(';
    listAux += "'"+lista[i]["link"]+"'";
    listAux += ')">'+lista[i]["titulo"]+'</a></div>';
    listAux += '<div class="col-xs-4 col-artista">'+lista[i]["artista"]+'</div>';
    listAux += '<div class="col-xs-3 col-duracion">'+lista[i]["duracion"]+'</div>';
    listAux += '</div>';
  }
  return listAux;
}

function abrirLinkCancion(link){
  window.JSInterface.loadAmazonWeb(link);
}

function onOrderTitulo(){
    let a = this.listResut.slice();
    if(this.organizadoTitulo == 0 || this.organizadoTitulo == 2){
      a.sort(
        function(a,b){
          if (a["titulo"] < b["titulo"])
            return -1;
          if (a["titulo"] > b["titulo"])
            return 1;
          return 0;
        });
        $("#listaResultados").html(generarElementoLista(a));
        this.organizadoTitulo = 1;
    } else{
        a.sort(
          function(a,b){
            if (a["titulo"] > b["titulo"])
              return -1;
            if (a["titulo"] < b["titulo"])
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
          if (a["artista"] < b["artista"])
            return -1;
          if (a["artista"] > b["artista"])
            return 1;
          return 0;
        });
        $("#listaResultados").html(generarElementoLista(a));
        this.organizadoArtista = 1;
    } else{
        a.sort(
          function(a,b){
            if (a["artista"] > b["artista"])
              return -1;
            if (a["artista"] < b["artista"])
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
          if (a["duracion"] < b["duracion"])
            return -1;
          if (a["duracion"] > b["duracion"])
            return 1;
          return 0;
        });
        $("#listaResultados").html(generarElementoLista(a));
        this.organizadoDuracion = 1;
    } else{
        a.sort(
          function(a,b){
            if (a["duracion"] > b["duracion"])
              return -1;
            if (a["duracion"] < b["duracion"])
              return 1;
            return 0;
          });
          $("#listaResultados").html(generarElementoLista(a));
          this.organizadoDuracion = 2;
    }
  }

  function checkModoNocturno(modo){
    if (!modo) {
      $('link[id="cssSelect"]').attr('href','./css/master.css');
    }else {
      $('link[id="cssSelect"]').attr('href','./css/master_noct.css');
    }
  }
