var localgun;
var simpleSqlParser;

(function(exports){

    exports.init = function(lg) {
        localgun = lg;
    }
    exports.setParser = function(lg) {
        simpleSqlParser = lg;
    }

  exports.helpme = function() {
      console.log('HELP ME!' + localgun);
  };



  exports.sql = function(sql, callbackFn, schema) {
      console.log('function(schema, sql, callbackFn) : ');
      console.log('SQL: ' + sql);
      console.log('callbackFn: ' + callbackFn);
      console.log('schema: ' + schema);

      //console.log('simpleSqlParser: ' + simpleSqlParser);
      var ast = simpleSqlParser.sql2ast(sql);
      console.log('ast: ' + JSON.stringify(ast));
  };




  exports.ifNull = function(entry, callbackFn) {
      ifNull('default',entry, callbackFn);
  }
  exports.ifNull = function(schema,entry, callbackFn) {
      gun.get(schema).path(entry).not(function(pp) {
          callbackFn();
      });
  }





  exports.onChangeRecords = function(schema, table, callbackFn) {
    gun.get(schema).path(table).on().map(function(a,b){
      delete a["_"];
      callbackFn(a);
    },true);
  };
  exports.onChangeRecords = function(table, callbackFn) {
      onChangeRecords('default', table, callbackFn)
    };

}(typeof exports === 'undefined' ? this.share = {} : exports));
