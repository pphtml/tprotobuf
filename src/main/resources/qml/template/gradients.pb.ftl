node {
  name: "${nodeName}/Shape"
  op: "Const"
  attr {
    key: "dtype"
    value {
      type: DT_INT32
    }
  }
  attr {
    key: "value"
    value {
      tensor {
        dtype: DT_INT32
        tensor_shape {
          dim {
          }
        }
      }
    }
  }
}
node {
  name: "${nodeName}/grad_ys_0"
  op: "Const"
  attr {
    key: "value"
    value {
      tensor {
        dtype: DT_FLOAT
        tensor_shape {
        }
        float_val: 1.0
      }
    }
  }
  attr {
    key: "dtype"
    value {
      type: DT_FLOAT
    }
  }
}
node {
  name: "${nodeName}/Fill"
  op: "Fill"
  input: "${nodeName}/Shape"
  input: "${nodeName}/grad_ys_0"
  attr {
    key: "T"
    value {
      type: DT_FLOAT
    }
  }
  attr {
    key: "index_type"
    value {
      type: DT_INT32
    }
  }
}



node {
  name: "${nodeName}/reduceMeanNode_grad/Reshape/shape"
  op: "Const"
  attr {
    key: "value"
    value {
      tensor {
        dtype: DT_INT32
        tensor_shape {
          dim {
            size: 1
          }
        }
        int_val: 1
      }
    }
  }
  attr {
    key: "dtype"
    value {
      type: DT_INT32
    }
  }
}
node {
  name: "${nodeName}/reduceMeanNode_grad/Reshape"
  op: "Reshape"
  input: "${nodeName}/Fill"
  input: "${nodeName}/reduceMeanNode_grad/Reshape/shape"
  attr {
    key: "T"
    value {
      type: DT_FLOAT
    }
  }
  attr {
    key: "Tshape"
    value {
      type: DT_INT32
    }
  }
}


node {
  name: "${nodeName}/reduceMeanNode_grad/Const"
  op: "Const"
  attr {
    key: "value"
    value {
      tensor {
        dtype: DT_INT32
        tensor_shape {
          dim {
            size: 1
          }
        }
        int_val: 100
      }
    }
  }
  attr {
    key: "dtype"
    value {
      type: DT_INT32
    }
  }
}
node {
  name: "${nodeName}/reduceMeanNode_grad/Tile"
  op: "Tile"
  input: "${nodeName}/reduceMeanNode_grad/Reshape"
  input: "${nodeName}/reduceMeanNode_grad/Const"
  attr {
    key: "Tmultiples"
    value {
      type: DT_INT32
    }
  }
  attr {
    key: "T"
    value {
      type: DT_FLOAT
    }
  }
}
node {
  name: "${nodeName}/reduceMeanNode_grad/Const_1"
  op: "Const"
  attr {
    key: "value"
    value {
      tensor {
        dtype: DT_FLOAT
        tensor_shape {
        }
        float_val: 100.0
      }
    }
  }
  attr {
    key: "dtype"
    value {
      type: DT_FLOAT
    }
  }
}
node {
  name: "${nodeName}/reduceMeanNode_grad/truediv"
  op: "RealDiv"
  input: "${nodeName}/reduceMeanNode_grad/Tile"
  input: "${nodeName}/reduceMeanNode_grad/Const_1"
  attr {
    key: "T"
    value {
      type: DT_FLOAT
    }
  }
}
node {
  name: "${nodeName}/squareNode_grad/Const"
  op: "Const"
  input: "^${nodeName}/reduceMeanNode_grad/truediv"
  attr {
    key: "value"
    value {
      tensor {
        dtype: DT_FLOAT
        tensor_shape {
        }
        float_val: 2.0
      }
    }
  }
  attr {
    key: "dtype"
    value {
      type: DT_FLOAT
    }
  }
}
node {
  name: "${nodeName}/squareNode_grad/Mul"
  op: "Mul"
  input: "${opNodeName}"
  input: "${nodeName}/squareNode_grad/Const"
  attr {
    key: "T"
    value {
      type: DT_FLOAT
    }
  }
}
node {
  name: "${nodeName}/squareNode_grad/Mul_1"
  op: "Mul"
  input: "${nodeName}/reduceMeanNode_grad/truediv"
  input: "${nodeName}/squareNode_grad/Mul"
  attr {
    key: "T"
    value {
      type: DT_FLOAT
    }
  }
}
node {
  name: "${nodeName}/sub_grad/Neg"
  op: "Neg"
  input: "${nodeName}/squareNode_grad/Mul_1"
  attr {
    key: "T"
    value {
      type: DT_FLOAT
    }
  }
}
node {
  name: "${nodeName}/add_1_grad/Shape"
  op: "Const"
  attr {
    key: "value"
    value {
      tensor {
        dtype: DT_INT32
        tensor_shape {
          dim {
            size: 1
          }
        }
        int_val: 100
      }
    }
  }
  attr {
    key: "dtype"
    value {
      type: DT_INT32
    }
  }
}
node {
  name: "${nodeName}/add_1_grad/Shape_1"
  op: "Const"
  attr {
    key: "value"
    value {
      tensor {
        dtype: DT_INT32
        tensor_shape {
          dim {
          }
        }
      }
    }
  }
  attr {
    key: "dtype"
    value {
      type: DT_INT32
    }
  }
}
node {
  name: "${nodeName}/add_1_grad/BroadcastGradientArgs"
  op: "BroadcastGradientArgs"
  input: "${nodeName}/add_1_grad/Shape"
  input: "${nodeName}/add_1_grad/Shape_1"
  attr {
    key: "T"
    value {
      type: DT_INT32
    }
  }
}
<#--node {-->
  <#--name: "${nodeName}/add_1_grad/Sum"-->
  <#--op: "Sum"-->
  <#--input: "${nodeName}/squareNode_grad/Mul_1"-->
  <#--input: "${nodeName}/add_1_grad/BroadcastGradientArgs"-->
  <#--attr {-->
    <#--key: "T"-->
    <#--value {-->
      <#--type: DT_FLOAT-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "keep_dims"-->
    <#--value {-->
      <#--b: false-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "Tidx"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/add_1_grad/Reshape"-->
  <#--op: "Reshape"-->
  <#--input: "${nodeName}/add_1_grad/Sum"-->
  <#--input: "${nodeName}/add_1_grad/Shape"-->
  <#--attr {-->
    <#--key: "T"-->
    <#--value {-->
      <#--type: DT_FLOAT-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "Tshape"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/add_1_grad/Sum_1"-->
  <#--op: "Sum"-->
  <#--input: "${nodeName}/squareNode_grad/Mul_1"-->
  <#--input: "${nodeName}/add_1_grad/BroadcastGradientArgs:1"-->
  <#--attr {-->
    <#--key: "T"-->
    <#--value {-->
      <#--type: DT_FLOAT-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "keep_dims"-->
    <#--value {-->
      <#--b: false-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "Tidx"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/add_1_grad/Reshape_1"-->
  <#--op: "Reshape"-->
  <#--input: "${nodeName}/add_1_grad/Sum_1"-->
  <#--input: "${nodeName}/add_1_grad/Shape_1"-->
  <#--attr {-->
    <#--key: "T"-->
    <#--value {-->
      <#--type: DT_FLOAT-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "Tshape"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/add_grad/Shape"-->
  <#--op: "Const"-->
  <#--attr {-->
    <#--key: "dtype"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "value"-->
    <#--value {-->
      <#--tensor {-->
        <#--dtype: DT_INT32-->
        <#--tensor_shape {-->
          <#--dim {-->
            <#--size: 1-->
          <#--}-->
        <#--}-->
        <#--int_val: 100-->
      <#--}-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/add_grad/Shape_1"-->
  <#--op: "Const"-->
  <#--attr {-->
    <#--key: "value"-->
    <#--value {-->
      <#--tensor {-->
        <#--dtype: DT_INT32-->
        <#--tensor_shape {-->
          <#--dim {-->
          <#--}-->
        <#--}-->
      <#--}-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "dtype"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/add_grad/BroadcastGradientArgs"-->
  <#--op: "BroadcastGradientArgs"-->
  <#--input: "${nodeName}/add_grad/Shape"-->
  <#--input: "${nodeName}/add_grad/Shape_1"-->
  <#--attr {-->
    <#--key: "T"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/add_grad/Sum"-->
  <#--op: "Sum"-->
  <#--input: "${nodeName}/add_1_grad/Reshape"-->
  <#--input: "${nodeName}/add_grad/BroadcastGradientArgs"-->
  <#--attr {-->
    <#--key: "keep_dims"-->
    <#--value {-->
      <#--b: false-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "Tidx"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "T"-->
    <#--value {-->
      <#--type: DT_FLOAT-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/add_grad/Reshape"-->
  <#--op: "Reshape"-->
  <#--input: "${nodeName}/add_grad/Sum"-->
  <#--input: "${nodeName}/add_grad/Shape"-->
  <#--attr {-->
    <#--key: "T"-->
    <#--value {-->
      <#--type: DT_FLOAT-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "Tshape"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/add_grad/Sum_1"-->
  <#--op: "Sum"-->
  <#--input: "${nodeName}/add_1_grad/Reshape"-->
  <#--input: "${nodeName}/add_grad/BroadcastGradientArgs:1"-->
  <#--attr {-->
    <#--key: "keep_dims"-->
    <#--value {-->
      <#--b: false-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "Tidx"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "T"-->
    <#--value {-->
      <#--type: DT_FLOAT-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/add_grad/Reshape_1"-->
  <#--op: "Reshape"-->
  <#--input: "${nodeName}/add_grad/Sum_1"-->
  <#--input: "${nodeName}/add_grad/Shape_1"-->
  <#--attr {-->
    <#--key: "T"-->
    <#--value {-->
      <#--type: DT_FLOAT-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "Tshape"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/mul_grad/Shape"-->
  <#--op: "Const"-->
  <#--attr {-->
    <#--key: "value"-->
    <#--value {-->
      <#--tensor {-->
        <#--dtype: DT_INT32-->
        <#--tensor_shape {-->
          <#--dim {-->
          <#--}-->
        <#--}-->
      <#--}-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "dtype"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/mul_grad/Shape_1"-->
  <#--op: "Const"-->
  <#--attr {-->
    <#--key: "value"-->
    <#--value {-->
      <#--tensor {-->
        <#--dtype: DT_INT32-->
        <#--tensor_shape {-->
          <#--dim {-->
            <#--size: 1-->
          <#--}-->
        <#--}-->
        <#--int_val: 100-->
      <#--}-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "dtype"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/mul_grad/BroadcastGradientArgs"-->
  <#--op: "BroadcastGradientArgs"-->
  <#--input: "${nodeName}/mul_grad/Shape"-->
  <#--input: "${nodeName}/mul_grad/Shape_1"-->
  <#--attr {-->
    <#--key: "T"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/mul_grad/Mul"-->
  <#--op: "Mul"-->
  <#--input: "${nodeName}/add_grad/Reshape"-->
  <#--input: "mul/y"-->
  <#--attr {-->
    <#--key: "T"-->
    <#--value {-->
      <#--type: DT_FLOAT-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/mul_grad/Sum"-->
  <#--op: "Sum"-->
  <#--input: "${nodeName}/mul_grad/Mul"-->
  <#--input: "${nodeName}/mul_grad/BroadcastGradientArgs"-->
  <#--attr {-->
    <#--key: "keep_dims"-->
    <#--value {-->
      <#--b: false-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "Tidx"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "T"-->
    <#--value {-->
      <#--type: DT_FLOAT-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/mul_grad/Reshape"-->
  <#--op: "Reshape"-->
  <#--input: "${nodeName}/mul_grad/Sum"-->
  <#--input: "${nodeName}/mul_grad/Shape"-->
  <#--attr {-->
    <#--key: "T"-->
    <#--value {-->
      <#--type: DT_FLOAT-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "Tshape"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/mul_grad/Mul_1"-->
  <#--op: "Mul"-->
  <#--input: "a/read"-->
  <#--input: "${nodeName}/add_grad/Reshape"-->
  <#--attr {-->
    <#--key: "T"-->
    <#--value {-->
      <#--type: DT_FLOAT-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/mul_grad/Sum_1"-->
  <#--op: "Sum"-->
  <#--input: "${nodeName}/mul_grad/Mul_1"-->
  <#--input: "${nodeName}/mul_grad/BroadcastGradientArgs:1"-->
  <#--attr {-->
    <#--key: "keep_dims"-->
    <#--value {-->
      <#--b: false-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "Tidx"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "T"-->
    <#--value {-->
      <#--type: DT_FLOAT-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "${nodeName}/mul_grad/Reshape_1"-->
  <#--op: "Reshape"-->
  <#--input: "${nodeName}/mul_grad/Sum_1"-->
  <#--input: "${nodeName}/mul_grad/Shape_1"-->
  <#--attr {-->
    <#--key: "T"-->
    <#--value {-->
      <#--type: DT_FLOAT-->
    <#--}-->
  <#--}-->
  <#--attr {-->
    <#--key: "Tshape"-->
    <#--value {-->
      <#--type: DT_INT32-->
    <#--}-->
  <#--}-->
<#--}-->
<#--node {-->
  <#--name: "init"-->
  <#--op: "NoOp"-->
  <#--input: "^a/Assign"-->
  <#--input: "^b/Assign"-->
  <#--input: "^c/Assign"-->
<#--}-->
