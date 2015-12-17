$(function() {
    Tools.EnumUnitList('ManagerEnum','type');
    $(".form-config-sys-manager-inputBasic").data("formOptions", {
        bindEvents : function() {
            var $form = $(this);
            $form.find('.supplier-input-div').hide();
            $form.find('.employee-input-div').hide();
            $form.find('.custom-input-div').hide();
            if ($form.find('#type').val() == 'SUPPLIER'){
                $form.find('.supplier-input-div').show();
            } else if ($form.find('#type').val() == 'EMPLOYEE'){
                $form.find('.employee-input-div').show();
            } else if ($form.find('#type').val() == 'CUSTOM'){
                $form.find('.custom-input-div').show();
            }

            $form.find('#type').change(function(){
                $form.find('.supplier-input-div').hide();
                $form.find('#supplier_id').val('');
                $form.find('#supplier_name').val('');
                $form.find('.employee-input-div').hide();
                $form.find('#employee_id').val('');
                $form.find('#employee_name').val('');
                $form.find('.custom-input-div').hide();
                $form.find('#custom_id').val('');
                $form.find('#custom_name').val('');
                if ($(this).val() == 'SUPPLIER'){
                    $form.find('.supplier-input-div').show();
                } else if ($(this).val() == 'EMPLOYEE'){
                    $form.find('.employee-input-div').show();
                } else if ($(this).val() == 'CUSTOM'){
                    $form.find('.custom-input-div').show();
                }
            });

            $form.find(".supplier-select").click(function() {
                $(this).popupDialog({
                    url : WEB_ROOT + '/ucenter/supplier/select.htm',
                    callback : function(rowdata) {
                        $form.find("#supplier_name").val(rowdata.name).attr("readonly","readonly");
                        $form.find("#supplier_id").val(rowdata.id);
                        $form.find("#name").val(rowdata.name);
                        $form.find("#code").val(rowdata.code);
                    }
                })
            });

            $form.find(".employee-select").click(function() {
                $(this).popupDialog({
                    url : WEB_ROOT + '/ucenter/employee/select.htm',
                    callback : function(rowdata) {
                        $form.find("#employee_name").val(rowdata.name).attr("readonly","readonly");
                        $form.find("#employee_id").val(rowdata.id);
                        $form.find("#name").val(rowdata.name);
                        $form.find("#code").val(rowdata.code);
                    }
                })
            });

            $form.find(".custom-select").click(function() {
                $(this).popupDialog({
                    url : WEB_ROOT + '/ucenter/custom/select.htm',
                    callback : function(rowdata) {
                        $form.find("#custom_name").val(rowdata.name).attr("readonly","readonly");
                        $form.find("#custom_id").val(rowdata.id);
                        $form.find("#name").val(rowdata.name);
                        $form.find("#code").val(rowdata.code);
                    }
                })
            });
        }
    });
});