<#include "common.ftl" >
  <script type="text/javascript">
    alert('${errorMsg}');
    if('${uri}'=="{ctx}/main"){
    	window.location.href="${ctx}/index";
    }else{
    	window.parent.location.href="${ctx}/index";
    }
    
  </script>
