<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	<%--按钮--%>
    <a class="easyui-linkbutton" onclick="importItems()">一键导入商品数据到索引库</a>
</div>

<script type="text/javascript">
    function importItems() {
        $.post("/index/import", null, function () {
            $.messager.alert('提示', '商品数据导入完成！');
        });
    }
</script>