/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {

/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;

/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};

/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;

/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}


/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;

/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;

/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";

/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	var __weex_template__ = __webpack_require__(1)
	var __weex_style__ = __webpack_require__(2)
	var __weex_script__ = __webpack_require__(3)

	__weex_define__('@weex-component/de49fa6556ba9ad7394b29f9cbcda2c3', [], function(__weex_require__, __weex_exports__, __weex_module__) {

	    __weex_script__(__weex_module__, __weex_exports__, __weex_require__)
	    if (__weex_exports__.__esModule && __weex_exports__.default) {
	      __weex_module__.exports = __weex_exports__.default
	    }

	    __weex_module__.exports.template = __weex_template__

	    __weex_module__.exports.style = __weex_style__

	})

	__weex_bootstrap__('@weex-component/de49fa6556ba9ad7394b29f9cbcda2c3',undefined,undefined)

/***/ },
/* 1 */
/***/ function(module, exports) {

	module.exports = {
	  "type": "div",
	  "classList": [
	    "bundle"
	  ],
	  "id": function () {return this.id},
	  "events": {
	    "click": "clickitem",
	    "longpress": "longpress"
	  },
	  "children": [
	    {
	      "type": "image",
	      "attr": {
	        "src": function () {return this.icon_dir}
	      },
	      "classList": [
	        "pic"
	      ]
	    },
	    {
	      "type": "text",
	      "classList": [
	        "name"
	      ],
	      "attr": {
	        "value": function () {return this.name}
	      }
	    }
	  ]
	}

/***/ },
/* 2 */
/***/ function(module, exports) {

	module.exports = {
	  "pic": {
	    "width": 150,
	    "height": 150
	  },
	  "name": {
	    "fontSize": 20,
	    "textAlign": "center",
	    "flex": 1
	  },
	  "bundle": {
	    "marginTop": 10,
	    "marginRight": 10,
	    "marginBottom": 10,
	    "marginLeft": 10,
	    "width": 150,
	    "height": 200
	  }
	}

/***/ },
/* 3 */
/***/ function(module, exports) {

	module.exports = function(module, exports, __weex_require__){'use strict';

	var modal = __weex_require__('@weex-module/modal');
	var navigator = __weex_require__('@weex-module/navigator');
	module.exports = {
	  data: function () {return {
	    id: null,
	    name: null,
	    icon_dir: null
	  }},
	  created: function created() {
	    this.baseURL = 'file://assets/weex/pages/';
	  },
	  methods: {
	    clickitem: function clickitem(e) {
	      var params = {
	        'url': this.baseURL + 'mc-formula.js',
	        'animated': 'true'
	      };
	      navigator.push(params, function (e) {});
	    },
	    longpress: function longpress(e) {
	      var self = this;
	      modal.confirm({
	        message: "add " + this.name + " to shopping list?",
	        okTitle: "yes",
	        cancelTitle: "no"
	      }, function (res) {
	        modal.toast({ 'message': res, 'duration': 1 });
	        if (res == "yes") {
	          modal.toast({ 'message': "yes click", 'duration': 1 });
	          self.$dispatch('additem2shoppinglist', { id: self.id });
	        }
	      });
	    }
	  }
	};}
	/* generated by weex-loader */


/***/ }
/******/ ]);