var r = PrimeFaces.widget.ColumnToggler.prototype.render;
PrimeFaces.widget.ColumnToggler.prototype.render = function(){
    r.apply(this, arguments);
    this.panel.prepend('<div style="position: relative;margin: 0;padding: .4em;display: inline-block;"><input class="ui-inputfield ui-inputtext ui-widget ui-state-default ui-corner-all" type="text" role="textbox"><div>');
    $('.ui-columntoggler .ui-inputfield').keyup(function(){
        var filter = $(this).val().toLowerCase();
        $('.ui-columntoggler .ui-inputfield').parent().parent().find('.ui-columntoggler-item label').each(function(){
            console.debug(filter+' '+this.innerHTML.toLowerCase());
            if(this.innerHTML.toLowerCase().indexOf(filter) < 0){
                $(this).parent().css('display','none');
            }else{
                $(this).parent().css('display','');
            }
        });
    });
};