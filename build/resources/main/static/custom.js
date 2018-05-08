function changeItemsPerPage() {

    var itemsPerPage = $( ".items-per-page-selection").val();

    console.log('Items per page', itemsPerPage);

    window.location.href = URI(window.location.href)
            .removeQuery('size')
            .addQuery({size: itemsPerPage})
            .duplicateQueryParameters(false)
            .normalizeQuery();
}

function goToPage(pageNumber) {

    window.location.href = URI(window.location.href)
            .removeQuery('page')
            .addQuery({page: pageNumber})
            .duplicateQueryParameters(false)
            .normalizeQuery();
}

$(document).ready( function () {
    $('.table_datatable').DataTable({
//        columnDefs: [ {
//            orderable: false,
//            className: 'select-checkbox',
//            targets:   0
//        } ],
//        select: {
//            style:    'os',
//            selector: 'td:first-child'
//        },
        order: [[ 1, 'asc' ]],
        "language": {
            "sEmptyTable":      "Keine Daten in der Tabelle vorhanden",
            "sInfo":            "_START_ bis _END_ von _TOTAL_ Einträgen",
            "sInfoEmpty":       "0 bis 0 von 0 Einträgen",
            "sInfoFiltered":    "(gefiltert von _MAX_ Einträgen)",
            "sInfoPostFix":     "",
            "sInfoThousands":   ".",
            "sLengthMenu":      "_MENU_ Einträge anzeigen",
            "sLoadingRecords":  "Wird geladen...",
            "sProcessing":      "Bitte warten...",
            "sSearch":          "Suchen",
            "sZeroRecords":     "Keine Einträge vorhanden.",
            "oPaginate": {
                "sFirst":       "Erste",
                "sPrevious":    "Zurück",
                "sNext":        "Nächste",
                "sLast":        "Letzte"
            },
            "oAria": {
                "sSortAscending":  ": aktivieren, um Spalte aufsteigend zu sortieren",
                "sSortDescending": ": aktivieren, um Spalte absteigend zu sortieren"
            },
            select: {
                    rows: {
                    _: '%d Zeilen ausgewählt',
                    0: 'Zum Auswählen auf eine Zeile klicken',
                    1: '1 Zeile ausgewählt'
                    }
            }
        }
    });


    $(".chosen-select").chosen();

    $(".station-select").change(() => {
        $('#location-id').val($(".station-select").val());
        $('#location-name').val("Station: " + $( ".station-select option:selected" ).text());
    });

    $(".vehicle-select").change(() => {
        $('#location-id').val($(".vehicle-select").val());
        $('#location-name').val("Vehicle: " + $( ".vehicle-select option:selected" ).text());
    });

    $('#remove-location-button').click(() => {
        $('#location-id').val("");
        $('#location-name').val("");
    })



    /*
     * Replace all SVG images with inline SVG
     */
    $('img.svg').each(function(){
        var $img = $(this);
        var imgID = $img.attr('id');
        var imgClass = $img.attr('class');
        var imgURL = $img.attr('src');

        $.get(imgURL, function(data) {
            // Get the SVG tag, ignore the rest
            var $svg = $(data).find('svg');

            // Add replaced image's ID to the new SVG
            if(typeof imgID !== 'undefined') {
                $svg = $svg.attr('id', imgID);
            }
            // Add replaced image's classes to the new SVG
            if(typeof imgClass !== 'undefined') {
                $svg = $svg.attr('class', imgClass+' replaced-svg');
            }

            // Remove any invalid XML tags as per http://validator.w3.org
            $svg = $svg.removeAttr('xmlns:a');

            // Check if the viewport is set, if the viewport is not set the SVG wont't scale.
            if(!$svg.attr('viewBox') && $svg.attr('height') && $svg.attr('width')) {
                $svg.attr('viewBox', '0 0 ' + $svg.attr('height') + ' ' + $svg.attr('width'))
            }

            // Replace image with new SVG
            $img.replaceWith($svg);

        }, 'xml');

    });

} );