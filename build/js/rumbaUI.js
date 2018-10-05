(function (_, Kotlin, $module$kotlin_react_dom, $module$kotlin_react) {
  'use strict';
  var $$importsForInline$$ = _.$$importsForInline$$ || (_.$$importsForInline$$ = {});
  var IllegalStateException_init = Kotlin.kotlin.IllegalStateException_init;
  var Unit = Kotlin.kotlin.Unit;
  var render = $module$kotlin_react_dom.react.dom.render_2955dm$;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var Enum = Kotlin.kotlin.Enum;
  var throwISE = Kotlin.throwISE;
  var Kind_INTERFACE = Kotlin.Kind.INTERFACE;
  var emptyList = Kotlin.kotlin.collections.emptyList_287e2$;
  var setState = $module$kotlin_react.react.setState_kpl3tw$;
  var RComponent_init = $module$kotlin_react.react.RComponent_init_lqgejo$;
  var RComponent = $module$kotlin_react.react.RComponent;
  var getKClass = Kotlin.getKClass;
  Format.prototype = Object.create(Enum.prototype);
  Format.prototype.constructor = Format;
  NmosEntity$Node.prototype = Object.create(NmosEntity.prototype);
  NmosEntity$Node.prototype.constructor = NmosEntity$Node;
  NmosEntity$Device.prototype = Object.create(NmosEntity.prototype);
  NmosEntity$Device.prototype.constructor = NmosEntity$Device;
  NmosEntity$Sender.prototype = Object.create(NmosEntity.prototype);
  NmosEntity$Sender.prototype.constructor = NmosEntity$Sender;
  NmosEntity$Receiver.prototype = Object.create(NmosEntity.prototype);
  NmosEntity$Receiver.prototype.constructor = NmosEntity$Receiver;
  NmosType.prototype = Object.create(Enum.prototype);
  NmosType.prototype.constructor = NmosType;
  Nmos.prototype = Object.create(RComponent.prototype);
  Nmos.prototype.constructor = Nmos;
  function main$lambda$lambda($receiver) {
    nmos($receiver);
    return Unit;
  }
  function main$lambda(it) {
    var tmp$;
    tmp$ = document.getElementById('root');
    if (tmp$ == null) {
      throw IllegalStateException_init();
    }
    var root = tmp$;
    render(root, void 0, main$lambda$lambda);
    return Unit;
  }
  function main(args) {
    window.onload = main$lambda;
  }
  function Tag(key, value) {
    this.key = key;
    this.value = value;
  }
  Tag.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Tag',
    interfaces: []
  };
  Tag.prototype.component1 = function () {
    return this.key;
  };
  Tag.prototype.component2 = function () {
    return this.value;
  };
  Tag.prototype.copy_kwv3np$ = function (key, value) {
    return new Tag(key === void 0 ? this.key : key, value === void 0 ? this.value : value);
  };
  Tag.prototype.toString = function () {
    return 'Tag(key=' + Kotlin.toString(this.key) + (', value=' + Kotlin.toString(this.value)) + ')';
  };
  Tag.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.key) | 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    return result;
  };
  Tag.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.key, other.key) && Kotlin.equals(this.value, other.value)))));
  };
  function NmosConnection(sender, receiver) {
    this.sender = sender;
    this.receiver = receiver;
  }
  NmosConnection.prototype.toString = function () {
    return this.sender + '  <=>  ' + this.receiver;
  };
  NmosConnection.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'NmosConnection',
    interfaces: []
  };
  NmosConnection.prototype.component1 = function () {
    return this.sender;
  };
  NmosConnection.prototype.component2 = function () {
    return this.receiver;
  };
  NmosConnection.prototype.copy_puj7f4$ = function (sender, receiver) {
    return new NmosConnection(sender === void 0 ? this.sender : sender, receiver === void 0 ? this.receiver : receiver);
  };
  NmosConnection.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.sender) | 0;
    result = result * 31 + Kotlin.hashCode(this.receiver) | 0;
    return result;
  };
  NmosConnection.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.sender, other.sender) && Kotlin.equals(this.receiver, other.receiver)))));
  };
  function Format(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function Format_initFields() {
    Format_initFields = function () {
    };
    Format$UNKNOWN_instance = new Format('UNKNOWN', 0);
    Format$VIDEO_instance = new Format('VIDEO', 1);
    Format$AUDIO_instance = new Format('AUDIO', 2);
    Format$DATA_instance = new Format('DATA', 3);
    Format$MUX_instance = new Format('MUX', 4);
    Format$UNRECOGNIZED_instance = new Format('UNRECOGNIZED', 5);
  }
  var Format$UNKNOWN_instance;
  function Format$UNKNOWN_getInstance() {
    Format_initFields();
    return Format$UNKNOWN_instance;
  }
  var Format$VIDEO_instance;
  function Format$VIDEO_getInstance() {
    Format_initFields();
    return Format$VIDEO_instance;
  }
  var Format$AUDIO_instance;
  function Format$AUDIO_getInstance() {
    Format_initFields();
    return Format$AUDIO_instance;
  }
  var Format$DATA_instance;
  function Format$DATA_getInstance() {
    Format_initFields();
    return Format$DATA_instance;
  }
  var Format$MUX_instance;
  function Format$MUX_getInstance() {
    Format_initFields();
    return Format$MUX_instance;
  }
  var Format$UNRECOGNIZED_instance;
  function Format$UNRECOGNIZED_getInstance() {
    Format_initFields();
    return Format$UNRECOGNIZED_instance;
  }
  Format.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Format',
    interfaces: [Enum]
  };
  function Format$values() {
    return [Format$UNKNOWN_getInstance(), Format$VIDEO_getInstance(), Format$AUDIO_getInstance(), Format$DATA_getInstance(), Format$MUX_getInstance(), Format$UNRECOGNIZED_getInstance()];
  }
  Format.values = Format$values;
  function Format$valueOf(name) {
    switch (name) {
      case 'UNKNOWN':
        return Format$UNKNOWN_getInstance();
      case 'VIDEO':
        return Format$VIDEO_getInstance();
      case 'AUDIO':
        return Format$AUDIO_getInstance();
      case 'DATA':
        return Format$DATA_getInstance();
      case 'MUX':
        return Format$MUX_getInstance();
      case 'UNRECOGNIZED':
        return Format$UNRECOGNIZED_getInstance();
      default:throwISE('No enum constant rumba.nmos.Format.' + name);
    }
  }
  Format.valueOf_61zpoe$ = Format$valueOf;
  function INmosEntity() {
  }
  INmosEntity.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'INmosEntity',
    interfaces: []
  };
  function NmosEntity() {
  }
  function NmosEntity$Node(uuid, label, description, tags) {
    if (tags === void 0)
      tags = emptyList();
    NmosEntity.call(this);
    this.uuid_qe5uq3$_0 = uuid;
    this.label = label;
    this.description = description;
    this.tags = tags;
  }
  Object.defineProperty(NmosEntity$Node.prototype, 'uuid', {
    get: function () {
      return this.uuid_qe5uq3$_0;
    }
  });
  NmosEntity$Node.prototype.toString = function () {
    return this.label.length > 0 ? this.label : this.uuid;
  };
  NmosEntity$Node.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Node',
    interfaces: [NmosEntity]
  };
  NmosEntity$Node.prototype.component1 = function () {
    return this.uuid;
  };
  NmosEntity$Node.prototype.component2 = function () {
    return this.label;
  };
  NmosEntity$Node.prototype.component3 = function () {
    return this.description;
  };
  NmosEntity$Node.prototype.component4 = function () {
    return this.tags;
  };
  NmosEntity$Node.prototype.copy_yd6erl$ = function (uuid, label, description, tags) {
    return new NmosEntity$Node(uuid === void 0 ? this.uuid : uuid, label === void 0 ? this.label : label, description === void 0 ? this.description : description, tags === void 0 ? this.tags : tags);
  };
  NmosEntity$Node.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.uuid) | 0;
    result = result * 31 + Kotlin.hashCode(this.label) | 0;
    result = result * 31 + Kotlin.hashCode(this.description) | 0;
    result = result * 31 + Kotlin.hashCode(this.tags) | 0;
    return result;
  };
  NmosEntity$Node.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.uuid, other.uuid) && Kotlin.equals(this.label, other.label) && Kotlin.equals(this.description, other.description) && Kotlin.equals(this.tags, other.tags)))));
  };
  function NmosEntity$Device(uuid, label, nodeId, description, tags) {
    NmosEntity.call(this);
    this.uuid_1hwumh$_0 = uuid;
    this.label = label;
    this.nodeId = nodeId;
    this.description = description;
    this.tags = tags;
  }
  Object.defineProperty(NmosEntity$Device.prototype, 'uuid', {
    get: function () {
      return this.uuid_1hwumh$_0;
    }
  });
  NmosEntity$Device.prototype.toString = function () {
    return this.label.length > 0 ? this.label : this.uuid;
  };
  NmosEntity$Device.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Device',
    interfaces: [NmosEntity]
  };
  NmosEntity$Device.prototype.component1 = function () {
    return this.uuid;
  };
  NmosEntity$Device.prototype.component2 = function () {
    return this.label;
  };
  NmosEntity$Device.prototype.component3 = function () {
    return this.nodeId;
  };
  NmosEntity$Device.prototype.component4 = function () {
    return this.description;
  };
  NmosEntity$Device.prototype.component5 = function () {
    return this.tags;
  };
  NmosEntity$Device.prototype.copy_op88vj$ = function (uuid, label, nodeId, description, tags) {
    return new NmosEntity$Device(uuid === void 0 ? this.uuid : uuid, label === void 0 ? this.label : label, nodeId === void 0 ? this.nodeId : nodeId, description === void 0 ? this.description : description, tags === void 0 ? this.tags : tags);
  };
  NmosEntity$Device.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.uuid) | 0;
    result = result * 31 + Kotlin.hashCode(this.label) | 0;
    result = result * 31 + Kotlin.hashCode(this.nodeId) | 0;
    result = result * 31 + Kotlin.hashCode(this.description) | 0;
    result = result * 31 + Kotlin.hashCode(this.tags) | 0;
    return result;
  };
  NmosEntity$Device.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.uuid, other.uuid) && Kotlin.equals(this.label, other.label) && Kotlin.equals(this.nodeId, other.nodeId) && Kotlin.equals(this.description, other.description) && Kotlin.equals(this.tags, other.tags)))));
  };
  function NmosEntity$Sender(uuid, deviceId, label, description, active, format, tags) {
    NmosEntity.call(this);
    this.uuid_v8zd0o$_0 = uuid;
    this.deviceId = deviceId;
    this.label = label;
    this.description = description;
    this.active = active;
    this.format = format;
    this.tags = tags;
  }
  Object.defineProperty(NmosEntity$Sender.prototype, 'uuid', {
    get: function () {
      return this.uuid_v8zd0o$_0;
    }
  });
  NmosEntity$Sender.prototype.toString = function () {
    return this.label.length > 0 ? this.label : this.uuid;
  };
  NmosEntity$Sender.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Sender',
    interfaces: [NmosEntity]
  };
  NmosEntity$Sender.prototype.component1 = function () {
    return this.uuid;
  };
  NmosEntity$Sender.prototype.component2 = function () {
    return this.deviceId;
  };
  NmosEntity$Sender.prototype.component3 = function () {
    return this.label;
  };
  NmosEntity$Sender.prototype.component4 = function () {
    return this.description;
  };
  NmosEntity$Sender.prototype.component5 = function () {
    return this.active;
  };
  NmosEntity$Sender.prototype.component6 = function () {
    return this.format;
  };
  NmosEntity$Sender.prototype.component7 = function () {
    return this.tags;
  };
  NmosEntity$Sender.prototype.copy_jt6a73$ = function (uuid, deviceId, label, description, active, format, tags) {
    return new NmosEntity$Sender(uuid === void 0 ? this.uuid : uuid, deviceId === void 0 ? this.deviceId : deviceId, label === void 0 ? this.label : label, description === void 0 ? this.description : description, active === void 0 ? this.active : active, format === void 0 ? this.format : format, tags === void 0 ? this.tags : tags);
  };
  NmosEntity$Sender.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.uuid) | 0;
    result = result * 31 + Kotlin.hashCode(this.deviceId) | 0;
    result = result * 31 + Kotlin.hashCode(this.label) | 0;
    result = result * 31 + Kotlin.hashCode(this.description) | 0;
    result = result * 31 + Kotlin.hashCode(this.active) | 0;
    result = result * 31 + Kotlin.hashCode(this.format) | 0;
    result = result * 31 + Kotlin.hashCode(this.tags) | 0;
    return result;
  };
  NmosEntity$Sender.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.uuid, other.uuid) && Kotlin.equals(this.deviceId, other.deviceId) && Kotlin.equals(this.label, other.label) && Kotlin.equals(this.description, other.description) && Kotlin.equals(this.active, other.active) && Kotlin.equals(this.format, other.format) && Kotlin.equals(this.tags, other.tags)))));
  };
  function NmosEntity$Receiver(uuid, deviceId, label, description, senderId, active, format, tags) {
    NmosEntity.call(this);
    this.uuid_yhmu8u$_0 = uuid;
    this.deviceId = deviceId;
    this.label = label;
    this.description = description;
    this.senderId = senderId;
    this.active = active;
    this.format = format;
    this.tags = tags;
  }
  Object.defineProperty(NmosEntity$Receiver.prototype, 'uuid', {
    get: function () {
      return this.uuid_yhmu8u$_0;
    }
  });
  NmosEntity$Receiver.prototype.toString = function () {
    return this.label.length > 0 ? this.label : this.uuid;
  };
  NmosEntity$Receiver.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Receiver',
    interfaces: [NmosEntity]
  };
  NmosEntity$Receiver.prototype.component1 = function () {
    return this.uuid;
  };
  NmosEntity$Receiver.prototype.component2 = function () {
    return this.deviceId;
  };
  NmosEntity$Receiver.prototype.component3 = function () {
    return this.label;
  };
  NmosEntity$Receiver.prototype.component4 = function () {
    return this.description;
  };
  NmosEntity$Receiver.prototype.component5 = function () {
    return this.senderId;
  };
  NmosEntity$Receiver.prototype.component6 = function () {
    return this.active;
  };
  NmosEntity$Receiver.prototype.component7 = function () {
    return this.format;
  };
  NmosEntity$Receiver.prototype.component8 = function () {
    return this.tags;
  };
  NmosEntity$Receiver.prototype.copy_9rnxvz$ = function (uuid, deviceId, label, description, senderId, active, format, tags) {
    return new NmosEntity$Receiver(uuid === void 0 ? this.uuid : uuid, deviceId === void 0 ? this.deviceId : deviceId, label === void 0 ? this.label : label, description === void 0 ? this.description : description, senderId === void 0 ? this.senderId : senderId, active === void 0 ? this.active : active, format === void 0 ? this.format : format, tags === void 0 ? this.tags : tags);
  };
  NmosEntity$Receiver.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.uuid) | 0;
    result = result * 31 + Kotlin.hashCode(this.deviceId) | 0;
    result = result * 31 + Kotlin.hashCode(this.label) | 0;
    result = result * 31 + Kotlin.hashCode(this.description) | 0;
    result = result * 31 + Kotlin.hashCode(this.senderId) | 0;
    result = result * 31 + Kotlin.hashCode(this.active) | 0;
    result = result * 31 + Kotlin.hashCode(this.format) | 0;
    result = result * 31 + Kotlin.hashCode(this.tags) | 0;
    return result;
  };
  NmosEntity$Receiver.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.uuid, other.uuid) && Kotlin.equals(this.deviceId, other.deviceId) && Kotlin.equals(this.label, other.label) && Kotlin.equals(this.description, other.description) && Kotlin.equals(this.senderId, other.senderId) && Kotlin.equals(this.active, other.active) && Kotlin.equals(this.format, other.format) && Kotlin.equals(this.tags, other.tags)))));
  };
  NmosEntity.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'NmosEntity',
    interfaces: [INmosEntity]
  };
  function NmosType(name, ordinal) {
    Enum.call(this);
    this.name$ = name;
    this.ordinal$ = ordinal;
  }
  function NmosType_initFields() {
    NmosType_initFields = function () {
    };
    NmosType$NODE_instance = new NmosType('NODE', 0);
    NmosType$DEVICE_instance = new NmosType('DEVICE', 1);
    NmosType$RECEIVER_instance = new NmosType('RECEIVER', 2);
    NmosType$SENDER_instance = new NmosType('SENDER', 3);
    NmosType$UNKNOWN_instance = new NmosType('UNKNOWN', 4);
  }
  var NmosType$NODE_instance;
  function NmosType$NODE_getInstance() {
    NmosType_initFields();
    return NmosType$NODE_instance;
  }
  var NmosType$DEVICE_instance;
  function NmosType$DEVICE_getInstance() {
    NmosType_initFields();
    return NmosType$DEVICE_instance;
  }
  var NmosType$RECEIVER_instance;
  function NmosType$RECEIVER_getInstance() {
    NmosType_initFields();
    return NmosType$RECEIVER_instance;
  }
  var NmosType$SENDER_instance;
  function NmosType$SENDER_getInstance() {
    NmosType_initFields();
    return NmosType$SENDER_instance;
  }
  var NmosType$UNKNOWN_instance;
  function NmosType$UNKNOWN_getInstance() {
    NmosType_initFields();
    return NmosType$UNKNOWN_instance;
  }
  NmosType.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'NmosType',
    interfaces: [Enum]
  };
  function NmosType$values() {
    return [NmosType$NODE_getInstance(), NmosType$DEVICE_getInstance(), NmosType$RECEIVER_getInstance(), NmosType$SENDER_getInstance(), NmosType$UNKNOWN_getInstance()];
  }
  NmosType.values = NmosType$values;
  function NmosType$valueOf(name) {
    switch (name) {
      case 'NODE':
        return NmosType$NODE_getInstance();
      case 'DEVICE':
        return NmosType$DEVICE_getInstance();
      case 'RECEIVER':
        return NmosType$RECEIVER_getInstance();
      case 'SENDER':
        return NmosType$SENDER_getInstance();
      case 'UNKNOWN':
        return NmosType$UNKNOWN_getInstance();
      default:throwISE('No enum constant rumba.nmos.NmosType.' + name);
    }
  }
  NmosType.valueOf_61zpoe$ = NmosType$valueOf;
  function IdAndType(id, type) {
    this.id = id;
    this.type = type;
  }
  IdAndType.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'IdAndType',
    interfaces: []
  };
  IdAndType.prototype.component1 = function () {
    return this.id;
  };
  IdAndType.prototype.component2 = function () {
    return this.type;
  };
  IdAndType.prototype.copy_p2yxth$ = function (id, type) {
    return new IdAndType(id === void 0 ? this.id : id, type === void 0 ? this.type : type);
  };
  IdAndType.prototype.toString = function () {
    return 'IdAndType(id=' + Kotlin.toString(this.id) + (', type=' + Kotlin.toString(this.type)) + ')';
  };
  IdAndType.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.id) | 0;
    result = result * 31 + Kotlin.hashCode(this.type) | 0;
    return result;
  };
  IdAndType.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.id, other.id) && Kotlin.equals(this.type, other.type)))));
  };
  function State() {
  }
  State.$metadata$ = {
    kind: Kind_INTERFACE,
    simpleName: 'State',
    interfaces: []
  };
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  function Nmos() {
    RComponent_init(this);
    this.state.nodes = ArrayList_init();
    this.getEntities_0('node');
  }
  var attributesMapOf = $module$kotlin_react_dom.$$importsForInline$$['kotlinx-html-js'].kotlinx.html.attributesMapOf_jyasbz$;
  var LI_init = $module$kotlin_react_dom.$$importsForInline$$['kotlinx-html-js'].kotlinx.html.LI;
  function li$lambda(closure$classes) {
    return function (it) {
      return new LI_init(attributesMapOf('class', closure$classes), it);
    };
  }
  var RDOMBuilder_init = $module$kotlin_react_dom.react.dom.RDOMBuilder;
  var H1_init = $module$kotlin_react_dom.$$importsForInline$$['kotlinx-html-js'].kotlinx.html.H1;
  function h1$lambda(closure$classes) {
    return function (it) {
      return new H1_init(attributesMapOf('class', closure$classes), it);
    };
  }
  var UL_init = $module$kotlin_react_dom.$$importsForInline$$['kotlinx-html-js'].kotlinx.html.UL;
  function ul$lambda(closure$classes) {
    return function (it) {
      return new UL_init(attributesMapOf('class', closure$classes), it);
    };
  }
  Nmos.prototype.render_ss14n$ = function ($receiver) {
    var $receiver_0 = new RDOMBuilder_init(h1$lambda(null));
    $receiver_0.unaryPlus_pdl1vz$('Nodes');
    $receiver.child_2usv9w$($receiver_0.create());
    var $receiver_0_0 = new RDOMBuilder_init(ul$lambda(null));
    var tmp$;
    tmp$ = this.state.nodes.iterator();
    while (tmp$.hasNext()) {
      var element = tmp$.next();
      var $receiver_0_1 = new RDOMBuilder_init(li$lambda(null));
      $receiver_0_1.unaryPlus_pdl1vz$(element.label);
      $receiver_0_0.child_2usv9w$($receiver_0_1.create());
    }
    $receiver.child_2usv9w$($receiver_0_0.create());
  };
  function Nmos$getEntities$lambda$lambda(closure$entity, this$Nmos) {
    return function (str) {
      var arr = JSON.parse(str);
      console.log(arr);
      var tmp$;
      for (tmp$ = 0; tmp$ !== arr.length; ++tmp$) {
        var element = arr[tmp$];
        var closure$entity_0 = closure$entity;
        this$Nmos.getEntityDetail_0(closure$entity_0, element);
      }
      return Unit;
    };
  }
  function Nmos$getEntities$lambda(closure$entity, this$Nmos) {
    return function (res) {
      return res.text().then(Nmos$getEntities$lambda$lambda(closure$entity, this$Nmos));
    };
  }
  Nmos.prototype.getEntities_0 = function (entity) {
    window.fetch(new Request('http://localhost:8080/api/v1/nmos/' + entity + 's')).then(Nmos$getEntities$lambda(entity, this));
  };
  function Nmos$getEntityDetail$lambda$lambda$lambda(closure$node) {
    return function ($receiver) {
      $receiver.nodes.add_11rb$(closure$node);
      return Unit;
    };
  }
  function Nmos$getEntityDetail$lambda$lambda(this$Nmos) {
    return function (e) {
      console.log('entity = ' + e);
      var node = JSON.parse(e);
      setState(this$Nmos, Nmos$getEntityDetail$lambda$lambda$lambda(node));
      return Unit;
    };
  }
  function Nmos$getEntityDetail$lambda(this$Nmos) {
    return function (it) {
      return it.text().then(Nmos$getEntityDetail$lambda$lambda(this$Nmos));
    };
  }
  Nmos.prototype.getEntityDetail_0 = function (entity, id) {
    window.fetch(new Request('http://localhost:8080/api/v1/nmos/' + entity + '/' + id)).then(Nmos$getEntityDetail$lambda(this));
  };
  Nmos.$metadata$ = {
    kind: Kind_CLASS,
    simpleName: 'Nmos',
    interfaces: [RComponent]
  };
  function nmos$lambda($receiver) {
    return Unit;
  }
  function nmos($receiver) {
    return $receiver.child_bzgiuu$(getKClass(Nmos), nmos$lambda);
  }
  var package$rumba = _.rumba || (_.rumba = {});
  package$rumba.main_kand9s$ = main;
  var package$nmos = package$rumba.nmos || (package$rumba.nmos = {});
  package$nmos.Tag = Tag;
  package$nmos.NmosConnection = NmosConnection;
  Object.defineProperty(Format, 'UNKNOWN', {
    get: Format$UNKNOWN_getInstance
  });
  Object.defineProperty(Format, 'VIDEO', {
    get: Format$VIDEO_getInstance
  });
  Object.defineProperty(Format, 'AUDIO', {
    get: Format$AUDIO_getInstance
  });
  Object.defineProperty(Format, 'DATA', {
    get: Format$DATA_getInstance
  });
  Object.defineProperty(Format, 'MUX', {
    get: Format$MUX_getInstance
  });
  Object.defineProperty(Format, 'UNRECOGNIZED', {
    get: Format$UNRECOGNIZED_getInstance
  });
  package$nmos.Format = Format;
  package$nmos.INmosEntity = INmosEntity;
  NmosEntity.Node = NmosEntity$Node;
  NmosEntity.Device = NmosEntity$Device;
  NmosEntity.Sender = NmosEntity$Sender;
  NmosEntity.Receiver = NmosEntity$Receiver;
  package$nmos.NmosEntity = NmosEntity;
  Object.defineProperty(NmosType, 'NODE', {
    get: NmosType$NODE_getInstance
  });
  Object.defineProperty(NmosType, 'DEVICE', {
    get: NmosType$DEVICE_getInstance
  });
  Object.defineProperty(NmosType, 'RECEIVER', {
    get: NmosType$RECEIVER_getInstance
  });
  Object.defineProperty(NmosType, 'SENDER', {
    get: NmosType$SENDER_getInstance
  });
  Object.defineProperty(NmosType, 'UNKNOWN', {
    get: NmosType$UNKNOWN_getInstance
  });
  package$nmos.NmosType = NmosType;
  package$nmos.IdAndType = IdAndType;
  package$nmos.State = State;
  $$importsForInline$$['kotlin-react-dom'] = $module$kotlin_react_dom;
  package$nmos.Nmos = Nmos;
  package$nmos.nmos_ss14n$ = nmos;
  main([]);
  Kotlin.defineModule('rumbaUI', _);
  return _;
}(module.exports, require('kotlin'), require('kotlin-react-dom'), require('kotlin-react')));

//# sourceMappingURL=rumbaUI.js.map
