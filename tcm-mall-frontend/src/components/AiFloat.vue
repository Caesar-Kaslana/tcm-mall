<template>
  <div class="ai-float">
    <div v-if="!visible" class="ai-fab" @click="open">
      <el-icon :size="24"><ChatDotRound /></el-icon>
    </div>

    <div v-else class="ai-panel">
      <div class="ai-panel-header">
        <span>中医 AI 助手</span>
        <el-icon class="close-btn" @click="close"><Close /></el-icon>
      </div>

      <div class="ai-messages" ref="msgContainer">
        <div v-if="messages.length === 0" class="ai-empty">
          <p>你好！我是中医商城 AI 助手，可以帮你解答：</p>
          <ul>
            <li>中药功效、禁忌、养生知识</li>
            <li>商城使用帮助（下单、售后等）</li>
          </ul>
        </div>
        <div v-for="(msg, i) in messages" :key="i" :class="['ai-msg', msg.role]">
          <div class="ai-msg-bubble">{{ msg.content }}</div>
        </div>
        <div v-if="loading" class="ai-msg assistant">
          <div class="ai-msg-bubble typing">
            <span></span><span></span><span></span>
          </div>
        </div>
      </div>

      <div class="ai-input-area">
        <el-input
          v-model="input"
          type="textarea"
          :rows="2"
          placeholder="输入你的问题..."
          @keydown.enter.exact.prevent="send"
        />
        <el-button type="primary" :disabled="!input.trim() || loading" @click="send">
          发送
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick, watch } from 'vue'
import { ChatDotRound, Close } from '@element-plus/icons-vue'
import { sendMessage } from '../api/ai'
import { ElMessage } from 'element-plus'

const visible = ref(false)
const input = ref('')
const loading = ref(false)
const msgContainer = ref(null)

const STORAGE_KEY = 'tcm_ai_history'
const messages = ref(JSON.parse(localStorage.getItem(STORAGE_KEY) || '[]'))

watch(messages, (val) => {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(val))
}, { deep: true })

const scrollToBottom = () => {
  nextTick(() => {
    if (msgContainer.value) {
      msgContainer.value.scrollTop = msgContainer.value.scrollHeight
    }
  })
}

const open = () => {
  visible.value = true
  scrollToBottom()
}

const close = () => {
  visible.value = false
}

const send = async () => {
  const text = input.value.trim()
  if (!text || loading.value) return

  messages.value.push({ role: 'user', content: text })
  input.value = ''
  scrollToBottom()
  loading.value = true

  try {
    const history = messages.value.slice(0, -1).map(m => ({
      role: m.role,
      content: m.content
    }))
    const res = await sendMessage(text, history)
    messages.value.push({ role: 'assistant', content: res.data.reply })
  } catch {
    ElMessage.error('AI 服务暂时不可用，请稍后再试')
  } finally {
    loading.value = false
    scrollToBottom()
  }
}
</script>

<style scoped>
.ai-fab {
  position: fixed; bottom: 24px; right: 24px; z-index: 9999;
  width: 56px; height: 56px; border-radius: 50%;
  background: #409EFF; color: #fff; display: flex;
  align-items: center; justify-content: center;
  cursor: pointer; box-shadow: 0 4px 16px rgba(0,0,0,0.2);
  animation: pulse 2s infinite;
  transition: transform 0.2s;
}
.ai-fab:hover { transform: scale(1.1); }
@keyframes pulse {
  0%, 100% { box-shadow: 0 4px 16px rgba(64,158,255,0.4); }
  50% { box-shadow: 0 4px 24px rgba(64,158,255,0.7); }
}

.ai-panel {
  position: fixed; bottom: 24px; right: 24px; z-index: 9999;
  width: 380px; height: 520px; border-radius: 12px;
  background: #fff; box-shadow: 0 8px 32px rgba(0,0,0,0.15);
  display: flex; flex-direction: column; overflow: hidden;
}

.ai-panel-header {
  background: #409EFF; color: #fff; padding: 14px 16px;
  display: flex; justify-content: space-between; align-items: center;
  font-size: 16px; font-weight: 500;
}
.close-btn { cursor: pointer; font-size: 18px; }
.close-btn:hover { opacity: 0.7; }

.ai-messages {
  flex: 1; overflow-y: auto; padding: 16px;
  background: #f5f7fa;
}
.ai-empty {
  text-align: center; color: #909399; margin-top: 40px;
  font-size: 14px; line-height: 2;
}
.ai-empty ul {
  text-align: left; padding-left: 20px; margin-top: 8px;
}

.ai-msg { margin-bottom: 12px; display: flex; }
.ai-msg.user { justify-content: flex-end; }
.ai-msg.assistant { justify-content: flex-start; }

.ai-msg-bubble {
  max-width: 80%; padding: 10px 14px; border-radius: 12px;
  font-size: 14px; line-height: 1.6; word-break: break-word;
}
.ai-msg.user .ai-msg-bubble {
  background: #409EFF; color: #fff;
  border-bottom-right-radius: 4px;
}
.ai-msg.assistant .ai-msg-bubble {
  background: #fff; color: #303133;
  border-bottom-left-radius: 4px;
  box-shadow: 0 1px 4px rgba(0,0,0,0.08);
}

.typing { display: flex; gap: 4px; padding: 14px 18px; }
.typing span {
  width: 8px; height: 8px; border-radius: 50%;
  background: #909399; animation: typing 1.4s infinite;
}
.typing span:nth-child(2) { animation-delay: 0.2s; }
.typing span:nth-child(3) { animation-delay: 0.4s; }
@keyframes typing {
  0%, 60%, 100% { opacity: 0.3; }
  30% { opacity: 1; }
}

.ai-input-area {
  padding: 10px 12px; border-top: 1px solid #e4e7ed;
  display: flex; gap: 8px; align-items: flex-end;
  background: #fff;
}
.ai-input-area .el-button {
  flex-shrink: 0; height: 40px;
}
</style>
