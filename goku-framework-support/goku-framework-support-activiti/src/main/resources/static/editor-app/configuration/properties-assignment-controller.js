/*
 * Activiti Modeler component part of the Activiti project
 * Copyright 2005-2014 Alfresco Software, Ltd. All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

/*
 * Assignment
 */
var KisBpmAssignmentCtrl = [ '$scope', '$modal', function($scope, $modal) {

    // Config for the modal window
    var opts = {
        template:  'editor-app/configuration/properties/assignment-popup.html?version=' + Date.now(),
        scope: $scope
    };

    // Open the dialog
    $modal(opts);
}];

var KisBpmAssignmentPopupCtrl = [ '$scope', '$http', function($scope, $http) {
    	
    // Put json representing assignment on scope
    if ($scope.property.value !== undefined && $scope.property.value !== null
        && $scope.property.value.assignment !== undefined
        && $scope.property.value.assignment !== null)
    {
        $scope.assignment = $scope.property.value.assignment;
    } else {
        $scope.assignment = {};
    }

    // if ($scope.assignment.candidateUsers == undefined || $scope.assignment.candidateUsers.length == 0)
    // {
    // 	$scope.assignment.candidateUsers = [{value: ''}];
    // }
    //
    // Click handler for + button after enum value
    // var userValueIndex = 1;
    // $scope.addCandidateUserValue = function(index) {
    //     $scope.assignment.candidateUsers.splice(index + 1, 0, {value: 'value ' + userValueIndex++});
    // };

    // Click handler for - button after enum value
    // $scope.removeCandidateUserValue = function(index) {
    //     $scope.assignment.candidateUsers.splice(index, 1);
    // };
    //
    // if ($scope.assignment.candidateGroups == undefined || $scope.assignment.candidateGroups.length == 0)
    // {
    // 	$scope.assignment.candidateGroups = [{value: ''}];
    // }

    // var groupValueIndex = 1;
    // $scope.addCandidateGroupValue = function(index) {
    //     $scope.assignment.candidateGroups.splice(index + 1, 0, {value: 'value ' + groupValueIndex++});
    // };

    // Click handler for - button after enum value
    // $scope.removeCandidateGroupValue = function(index) {
    //     $scope.assignment.candidateGroups.splice(index, 1);
    // };

    $scope.save = function() {
        $scope.property.value = {};
        handleAssignmentInput($scope);
        $scope.property.value.assignment = $scope.assignment;
        $scope.updatePropertyInModel($scope.property);
        $scope.close();
    };

    // Close button handler
    $scope.close = function() {
    	handleAssignmentInput($scope);
    	$scope.property.mode = 'read';
    	$scope.$hide();
    };
    
    var handleAssignmentInput = function($scope) {
    	// if ($scope.assignment.candidateUsers)
    	// {
	    // 	var emptyUsers = true;
	    // 	var toRemoveIndexes = [];
	    //     for (var i = 0; i < $scope.assignment.candidateUsers.length; i++)
	    //     {
	    //     	if ($scope.assignment.candidateUsers[i].value != '')
	    //     	{
	    //     		emptyUsers = false;
	    //     	}
	    //     	else
	    //     	{
	    //     		toRemoveIndexes[toRemoveIndexes.length] = i;
	    //     	}
	    //     }
	    //
	    //     for (var i = 0; i < toRemoveIndexes.length; i++)
	    //     {
	    //     	$scope.assignment.candidateUsers.splice(toRemoveIndexes[i], 1);
	    //     }
	    //
	    //     if (emptyUsers)
	    //     {
	    //     	$scope.assignment.candidateUsers = undefined;
	    //     }
    	// }
        //
    	// if ($scope.assignment.candidateGroups)
    	// {
	    //     var emptyGroups = true;
	    //     var toRemoveIndexes = [];
	    //     for (var i = 0; i < $scope.assignment.candidateGroups.length; i++)
	    //     {
	    //     	if ($scope.assignment.candidateGroups[i].value != '')
	    //     	{
	    //     		emptyGroups = false;
	    //     	}
	    //     	else
	    //     	{
	    //     		toRemoveIndexes[toRemoveIndexes.length] = i;
	    //     	}
	    //     }
	    //
	    //     for (var i = 0; i < toRemoveIndexes.length; i++)
	    //     {
	    //     	$scope.assignment.candidateGroups.splice(toRemoveIndexes[i], 1);
	    //     }
	    //
	    //     if (emptyGroups)
	    //     {
	    //     	$scope.assignment.candidateGroups = undefined;
	    //     }
    	// }
		return;
    };


	/*---------------------流程设计器扩展用户与用户组--------------------*/
	//参数初始化
	$scope.multiSelect = false;
	$scope.gridData = []; //表格数据
	$scope.gridDataName = 'gridData';
	$scope.selectTitle = '选择代理人';
	$scope.columnData = []; //表格列数据
	$scope.columnDataName = 'columnData';
	$scope.selectType = 0; //0-代理人，1-候选人，2-候选组
	$scope.totalServerItems = 0; //表格总条数

	//分页初始化
	$scope.pagingOptions = {
		pageSizes: [2, 10, 20, 30],//page 行数可选值
		pageSize: 10, //每页行数
		currentPage: 1, //当前显示页数
	};

	//数据监视
	$scope.dataWatch = function (){
		//分页数据监视
		$scope.$watch('pagingOptions', function (newValue, oldValue) {
			$scope.getPagedDataAsync($scope.pagingOptions.currentPage, $scope.pagingOptions.pageSize);
		},true);

		//当切换类型时，初始化当前页
		$scope.$watch('selectType', function (newValue, oldValue) {
			if(newValue != oldValue){
				$scope.pagingOptions.currentPage = 1;
				$scope.getPagedDataAsync($scope.pagingOptions.currentPage, $scope.pagingOptions.pageSize);
			}
		},true);

		//切换平台
		$scope.change = function(x){
			$scope.getPagedDataAsync($scope.pagingOptions.currentPage, $scope.pagingOptions.pageSize);
		};
	};
	$scope.dataWatch();

	//异步请求表格数据
	$scope.getPagedDataAsync = function(pageNum, pageSize){
		var url;
		if($scope.selectType == 0){
			url = '/roleList/page';
			$scope.columnData = $scope.roleColumns;
			$scope.multiSelect = false;
		} else if ($scope.selectType == 1) {
			$scope.multiSelect = true;
			url = '/user/userListByRoleId?roleId='+ $scope.roleId;
			$scope.columnData = $scope.userColumns;

		} else if ($scope.selectType == 2) {
			$scope.multiSelect = true;
			url = '/roleList/page';
			$scope.columnData = $scope.groupColumns;
		}

		pageSize = 10;
		// Update
		$http({
			method: 'GET',
			url: ACTIVITI.CONFIG.contextRoot+url,
			params:{
				'page': pageNum,
				'pageSize': pageSize
			},
		}).then(function successCallback(res) {
			var obj = res.data;
			$scope.gridData = obj.data;
			$scope.totalServerItems = obj.totalCount;
			$scope.pagingOptions.currentPage = obj.pageNumber;
			$scope.pagingOptions.pageSize = obj.pageSize;
		}, function errorCallback() {
			// 请求失败执行代码
			$scope.gridData = [];
			$scope.totalServerItems = 0;
		});
	}

	//表格属性配置
	$scope.gridOptions = {
		data: $scope.gridDataName,
		multiSelect: $scope.multiSelect,//不可多选
		enablePaging: true,
		pagingOptions: $scope.pagingOptions,
		totalServerItems: 'totalServerItems',
		i18n:'zh-cn',
		showFooter: true,
		showSelectionCheckbox: true,
		columnDefs : $scope.columnDataName,
		enableFooterTotalSelected: true,
		beforeSelectionChange: function (event) {
			var data = event.entity;

			if($scope.selectType == 0){//选审批角色
				event.entity.checked = !event.selected;
				$scope.assignment.roleName = data.roleName;
				$scope.roleId = data.id;
			} else if($scope.selectType == 1){//选择用户
				event.entity.checked = !event.selected;
				$scope.assignment.assignee =  event.entity.username;
				$scope.assignment.users = event.entity.username;
				// var obj = {value: data.roleName};
				// var obj1 = {value:event.entity.realName};
				// $scope.assignment.candidateUsers
				$scope.assignment.candidateUser = event.entity.realName;
				// if(!array_contain($scope.assignment.candidateUsers, obj1.realName)){
				// 	$scope.assignment.candidateUsers.push(obj1);
				// }
			}
			// else if($scope.selectType == 2){//候选组
			// 	var obj = {value: $scope.getGroupData(event.entity)};
			// 	if(!array_contain($scope.assignment.candidateGroups, obj.value)){
			// 		$scope.assignment.candidateGroups.push(obj);
			// 	}
			// }
			return true;
		}
	};

	$scope.getGroupData = function(data){
		var prefix = ['${projectId}_','${bankEnterpriseId}_','${coreEnterpriseId}_','${chainEnterpriseId}_'];
		var result = prefix[data.enterpriseType] + data.roleCode;
		return result;
	};

	$scope.roleColumns = [
		{
			field : 'roleName',
			displayName : '角色名称',
			minWidth: 100,
			width : '30%'
		},
		{
			field : 'roleDescription',
			displayName : '角色权限',
			minWidth: 100,
			width : '70%'
		},
	],
	//选择用户时表头
	$scope.userColumns = [
		{
			field : 'username',
			displayName : '员工工号',
			minWidth: 100,
			width : '20%'
		},
		{
			field : 'shortName',
			displayName : '昵称',
			minWidth: 100,
			width : '20%'
		},
		{
			field : 'realName',
			displayName : '姓名',
			minWidth: 100,
			width : '30%'
		},
		{
			field : 'mobile',
			displayName : '手机号',
			minWidth: 100,
			width : '30%'
		}
	];
	//选择用户组时表头
	$scope.groupColumns = [
		{
			field : 'pkid',
			type:'number',
			displayName : '角色Id',
			minWidth: 100,
			width : '16%'
		},
		{
			field : 'roleCode',
			displayName : '角色code',
			minWidth: 100,
			width : '16%'
		},
		{
			field : 'name',
			displayName : '角色名称',
			minWidth: 100,
			width : '25%'
		},
		{
			field : 'type',
			type:'number',
			displayName : '角色类型',
			minWidth: 100,
			width : '18%',
			cellTemplate : '<span>{{row.entity.type==1?"公有":"私有"}}</span>'
		}
	];

	//选择审批角色
	$scope.selectAssignee = function (data) {
		$scope.selectType = 0;
		$scope.selectTitle = '选择审批角色';
	};

	//选择某个角色下的用户
	$scope.selectCandidate = function () {
		$scope.selectType = 1;
		$scope.selectTitle = '选择该角色下的用户';
	};

	//候选组
	$scope.selectGroup = function () {
		$scope.selectType = 2;
		$scope.selectTitle = '选择候选组';
	};
}];

//声明----如果有此 contains 直接用最好
function array_contain(array, obj){
	for (var i = 0; i < array.length; i++){
		if (array[i].value == obj)//如果要求数据类型也一致，这里可使用恒等号===
			return true;
	}
	return false;
}
