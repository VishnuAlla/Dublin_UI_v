Application.$controller("CustomerProfilePageController", ["$scope", function($scope) {
    "use strict";
    $scope.pageDetails;
    $scope.messagetext = "";
    $scope.onPageReady = function() {
        debugger;
        document.title = "Customer Profile";
        //  $('.wm-app .list-group-item:first-child').addClass('active');
        $scope.$root.refreshBC($scope.activePageName, "Customer Profile", false, {
            pageDetails: $scope.Variables.PolicyProfileDetails.dataSet
        });
        $scope.pageDetails = $scope.Variables.PolicyProfileDetails.dataSet;
        if (sessionStorage.Breadcrumbs === undefined || sessionStorage.Breadcrumbs === "") {
            $scope.pageDetails = $scope.Variables.PolicyProfileDetails.dataSet;
        } else {
            var breadcrumbData = angular.fromJson(sessionStorage.Breadcrumbs);
            for (var i = 0; i < breadcrumbData.length; i++) {
                var item = breadcrumbData[i];
                if (item.pageName === $scope.activePageName) {
                    $scope.pageDetails = JSON.parse(item.pageData).pageDetails;
                    $scope.Variables.PolicyProfileDetails.dataSet = JSON.parse(item.pageData).pageDetails;
                    break;
                }
            }
        }
        if ($scope.pageDetails.navpath === "customerportfolio") {
            $scope.$root.refreshBC($scope.activePageName, "Customer Profile", false, {
                pageDetails: $scope.Variables.PolicyProfileDetails.dataSet
            });


            $scope.Variables.GetClientPolicyDetails.setInput("type", "policyId");
            if ($scope.pageDetails.navpath === "customerportfolio1") {
                debugger
                $scope.Widgets.tabs1.next();
                $scope.Variables.GetClientPolicyDetails.setInput("id", $scope.pageDetails[0].policydetails.PolicyId);
            } else {
                $scope.Variables.GetClientPolicyDetails.setInput("id", $scope.pageDetails.policydetails.PolicyId);
            }
            $scope.Variables.GetClientPolicyDetails.update({}, function(data) {});
        }

    };
    $scope.livelist1Click = function($event, $isolateScope) {
        $scope.Variables;
        $scope.messagetext1 = $isolateScope.item.PolicyStatus;
        $scope.Variables.GetClientPolicyDetails.setInput("type", "policyId");
        if ($isolateScope.item.PolicyId !== "") {
            $scope.Variables.GetClientPolicyDetails.setInput("id", $isolateScope.item.PolicyId);
            $scope.Variables.GetClientPolicyDetails.update({}, function(data) {});
            if (window.innerWidth < 1024) {
                var lefttarget = $('.left_container').find('.mobileleft_container');
                var righttarget = $('.custright_container').find('.mobileright_container');
                var target = $('.left_container');
                lefttarget.removeClass('mobileleft_container');
                righttarget.removeClass('mobileright_container');
                righttarget.addClass('nav_container');
                righttarget.removeClass('hidecontainer');
                lefttarget.addClass('mobileright_container');
                target.removeClass('left_container');
                target.addClass('hidecontainer');
            }
        } else {

            $scope.showToaster("pendingpolicy");
        }

    };
    $scope.showToaster = function(variableName) {
        $scope.Variables[variableName].notify();
    };
    $scope.btn_backClick = function($event, $isolateScope) {
        var lefttarget = $('.hidecontainer').find('.mobileright_container');
        var righttarget = $('.custright_container').find('.nav_container');
        var target = $('.hidecontainer');
        lefttarget.removeClass('mobileright_container');
        righttarget.removeClass('nav_container');
        righttarget.addClass('mobileright_container');
        righttarget.addClass('hidecontainer');
        lefttarget.addClass('mobileleft_container');
        target.removeClass('hidecontainer');
        target.addClass('left_container');
    };
    $scope.pendingpolicyonHide = function(variable, data) {};
}]);

Application.$controller("grid_transactionsController", ["$scope", "DialogService",
    function($scope, DialogService) {
        "use strict";
        $scope.ctrlScope = $scope;
        $scope.expandRow = function(rowObj) {

            $scope.$root.expandSelectedRow("customertransactions", rowObj, $scope.Widgets.grid_transactions.fullFieldDefs);
            DialogService.showDialog("Dialog_transactionsdetails");
            $scope.Widgets.grid_transactions.selectedItems.length = 0;
        };
    }
]);

Application.$controller("grid_NomineesDetailsController", ["$scope", "DialogService",
    function($scope, DialogService) {
        "use strict";
        $scope.ctrlScope = $scope;

        $scope.expandRow = function(rowObj) {

            $scope.$root.expandSelectedRow("Nomineedetails", rowObj, $scope.Widgets.grid_NomineesDetails.fullFieldDefs);
            DialogService.showDialog("Dialog_NomineeDetails");
            $scope.Widgets.grid_NomineesDetails.selectedItems.length = 0;
        };
    }
]);

Application.$controller("grid3Controller", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;
    }
]);

Application.$controller("Dialog_transactionsdetailsController", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;
    }
]);

Application.$controller("Dialog_NomineeDetailsController", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;
    }
]);